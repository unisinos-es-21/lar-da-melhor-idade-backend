package com.example.demo.util;

import com.example.demo.entity.InstitutionalizedEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class TupleUtil {

    private TupleUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<InstitutionalizedEntity> getTuples(Pageable pageable, JPAQuery<InstitutionalizedEntity> query, PathBuilder<?> entityPath) {
        return getTuples(pageable, query, entityPath, pageable.getSort());
    }

    public static List<InstitutionalizedEntity> getTuples(Pageable pageable, JPAQuery<InstitutionalizedEntity> query, PathBuilder<?> entityPath, Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(entityPath, sort);
        return getTuples(pageable, query, orderSpecifiers);
    }

    public static List<OrderSpecifier<?>> getOrderSpecifiers(PathBuilder<?> entityPath, Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort) {
            PathBuilder<Object> path = entityPath.get(order.getProperty());
            orderSpecifiers.add(new OrderSpecifier(Order.valueOf(order.getDirection().name()), path));
        }
        return orderSpecifiers;
    }

    public static List<InstitutionalizedEntity> getTuples(Pageable pageable, JPAQuery<InstitutionalizedEntity> query, List<OrderSpecifier<?>> orderSpecifiers) {
        orderSpecifiers.forEach(query::orderBy);
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());
        return query.fetch();
    }

}
