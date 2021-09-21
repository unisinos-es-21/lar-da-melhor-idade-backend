package com.example.demo.service;

import com.example.demo.entity.InstitutionalizedEntity;
import com.example.demo.entity.QInstitutionalizedEntity;
import com.example.demo.enumerator.GenderEnum;
import com.example.demo.exception.*;
import com.example.demo.repository.InstitutionalizedRepository;
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
public class InstitutionalizedService {

    private final InstitutionalizedRepository institutionalizedRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public static List<InstitutionalizedEntity> fetchQuery(Pageable pageable, JPAQuery<InstitutionalizedEntity> query, PathBuilder<?> entityPath) {
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

    public Page<InstitutionalizedEntity> findAll(Pageable pageable, Long id, String name, String cpf, String phone,
                                                 LocalDate birthDay, GenderEnum gender) {
        QInstitutionalizedEntity qInstitutionalized = QInstitutionalizedEntity.institutionalizedEntity;
        Predicate where = this.getWhere(qInstitutionalized, id, name, cpf, phone, birthDay, gender);
        List<InstitutionalizedEntity> list = this.getTuples(pageable, qInstitutionalized, where);
        long totalItems = this.getTotalItems(qInstitutionalized, where);

        return new PageImpl<>(list, pageable, totalItems);
    }

    private long getTotalItems(QInstitutionalizedEntity qInstitutionalized, Predicate where) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
        query.from(qInstitutionalized);
        query.where(where);
        return query.fetchCount();
    }

    public List<InstitutionalizedEntity> getTuples(Pageable pageable, QInstitutionalizedEntity qInstitutionalized, Predicate where) {
        JPAQuery<InstitutionalizedEntity> query = new JPAQuery<>(entityManager);
        query.select(qInstitutionalized);
        query.from(qInstitutionalized);
        query.where(where);
        PathBuilder<InstitutionalizedEntity> entityPath = new PathBuilder<>(InstitutionalizedEntity.class, "institutionalizedEntity");
        return fetchQuery(pageable, query, entityPath);
    }

    private Predicate getWhere(QInstitutionalizedEntity qInstitutionalized, Long id, String name, String cpf,
                               String phone, LocalDate birthDay, GenderEnum gender) {
        var predicateBuilder = new BooleanBuilder();
        if (Objects.nonNull(id)) {
            predicateBuilder.and(qInstitutionalized.id.eq(id));
        }
        if (Objects.nonNull(name)) {
            predicateBuilder.and(qInstitutionalized.name.containsIgnoreCase(name));
        }
        if (Objects.nonNull(cpf)) {
            predicateBuilder.and(qInstitutionalized.cpf.containsIgnoreCase(cpf));
        }
        if (Objects.nonNull(phone)) {
            predicateBuilder.and(qInstitutionalized.phone.containsIgnoreCase(phone));
        }
        if (Objects.nonNull(birthDay)) {
            predicateBuilder.and(qInstitutionalized.birthDay.eq(birthDay));
        }
        if (Objects.nonNull(gender)) {
            predicateBuilder.and(qInstitutionalized.gender.eq(gender));
        }
        return predicateBuilder.getValue();
    }

    public InstitutionalizedEntity create(InstitutionalizedEntity institutionalized) {
        if (Objects.nonNull(institutionalized.getId())) {
            throw new CreateEntityIdNotNullException();
        }
        if (institutionalizedRepository.existsByCpf(institutionalized.getCpf())) {
            throw new CpfDuplicatedException(institutionalized.getCpf());
        }
        return institutionalizedRepository.save(institutionalized);
    }

    public InstitutionalizedEntity update(Long id, InstitutionalizedEntity institutionalized) {
        if (Objects.isNull(institutionalized.getId())) {
            throw new UpdateEntityIdNullException();
        }
        if (institutionalizedRepository.existsById(institutionalized.getId())) {
            throw new RecordsNotFoundException(institutionalized.getId());
        }
        if (!id.equals(institutionalized.getId())) {
            throw new DifferentIDException();
        }
        return institutionalizedRepository.save(institutionalized);
    }

    public void delete(Long id) {
        if (institutionalizedRepository.existsById(id)) {
            throw new RecordsNotFoundException(id);
        }
        institutionalizedRepository.deleteById(id);
    }

}
