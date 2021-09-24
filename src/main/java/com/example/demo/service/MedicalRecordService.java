package com.example.demo.service;

import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.entity.MedicalRecordEntity;
import com.example.demo.entity.QMedicalRecordEntity;
import com.example.demo.enumerator.ReasonEnum;
import com.example.demo.exception.CreateEntityIdNotNullException;
import com.example.demo.exception.DifferentIDException;
import com.example.demo.exception.RecordsNotFoundException;
import com.example.demo.exception.UpdateEntityIdNullException;
import com.example.demo.repository.MedicalRecordRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public static List<MedicalRecordEntity> fetchQuery(Pageable pageable, JPAQuery<MedicalRecordEntity> query, PathBuilder<?> entityPath) {
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(entityPath, pageable.getSort());
        orderSpecifiers.forEach(query::orderBy);
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
        return query.fetch();
    }

    public static List<OrderSpecifier<?>> getOrderSpecifiers(PathBuilder<?> entityPath, Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort) {
            PathBuilder<Object> path = entityPath.get(order.getProperty());
            orderSpecifiers.add(new OrderSpecifier(Order.valueOf(order.getDirection().name()), path));
        }
        return orderSpecifiers;
    }

    public Page<MedicalRecordEntity> findAll(Pageable pageable, Long id, String institutionalizedName, Long institutionalizedId,
                                             LocalDate medicalAppointmentDate, String responsible,
                                             ReasonEnum reason, String cid) {
        QMedicalRecordEntity qMedicalRecord = QMedicalRecordEntity.medicalRecordEntity;
        Predicate where = this.getWhere(qMedicalRecord, id, institutionalizedName, institutionalizedId, medicalAppointmentDate, responsible, reason, cid);
        List<MedicalRecordEntity> list = this.getTuples(pageable, qMedicalRecord, where);
        long totalItems = this.getTotalItems(qMedicalRecord, where);
        return new PageImpl<>(list, pageable, totalItems);
    }

    private long getTotalItems(QMedicalRecordEntity qInstitutionalized, Predicate where) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        query.from(qInstitutionalized);
        query.where(where);
        return query.fetchCount();
    }

    public List<MedicalRecordEntity> getTuples(Pageable pageable, QMedicalRecordEntity qInstitutionalized, Predicate where) {
        JPAQuery<MedicalRecordEntity> query = new JPAQuery<>(entityManager);
        query.select(qInstitutionalized);
        query.from(qInstitutionalized);
        query.where(where);
        PathBuilder<MedicalRecordEntity> entityPath = new PathBuilder<>(MedicalRecordEntity.class, "medicalRecordEntity");
        return fetchQuery(pageable, query, entityPath);
    }

    private Predicate getWhere(QMedicalRecordEntity qMedicalRecord, Long id, String institutionalizedName, Long institutionalizedId,
                               LocalDate medicalAppointmentDate, String responsible,
                               ReasonEnum reason, String cid) {
        var predicateBuilder = new BooleanBuilder();
        if (Objects.nonNull(id)) {
            predicateBuilder.and(qMedicalRecord.id.eq(id));
        }
        if (Objects.nonNull(institutionalizedId)) {
            predicateBuilder.and(qMedicalRecord.institutionalized.id.eq(institutionalizedId));
        }
        if (Objects.nonNull(institutionalizedName)) {
            predicateBuilder.and(qMedicalRecord.institutionalized.name.containsIgnoreCase(institutionalizedName));
        }
        if (Objects.nonNull(medicalAppointmentDate)) {
            predicateBuilder.and(qMedicalRecord.medicalAppointmentDate.eq(medicalAppointmentDate));
        }
        if (Objects.nonNull(responsible)) {
            predicateBuilder.and(qMedicalRecord.responsible.containsIgnoreCase(responsible));
        }
        if (Objects.nonNull(cid)) {
            predicateBuilder.and(qMedicalRecord.cid.containsIgnoreCase(cid));
        }
        if (Objects.nonNull(reason)) {
            predicateBuilder.and(qMedicalRecord.reason.eq(reason));
        }
        return predicateBuilder.getValue();
    }

    public MedicalRecordEntity create(MedicalRecordEntity medicalRecord) {
        if (Objects.nonNull(medicalRecord.getId())) {
            throw new CreateEntityIdNotNullException();
        }
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecordEntity update(Long id, MedicalRecordEntity medicalRecord) {
        if (Objects.isNull(medicalRecord.getId())) {
            throw new UpdateEntityIdNullException();
        }
        if (medicalRecordRepository.existsById(medicalRecord.getId())) {
            throw new RecordsNotFoundException(medicalRecord.getId());
        }
        if (!id.equals(medicalRecord.getId())) {
            throw new DifferentIDException();
        }
        return medicalRecordRepository.save(medicalRecord);
    }

    public void delete(Long id) {
        if (medicalRecordRepository.existsById(id)) {
            throw new RecordsNotFoundException(id);
        }
        medicalRecordRepository.deleteById(id);
    }

    public MedicalRecordEntity findOne(Long id) {
        return medicalRecordRepository.findById(id).orElseThrow(() -> new RecordsNotFoundException(id));
    }

}
