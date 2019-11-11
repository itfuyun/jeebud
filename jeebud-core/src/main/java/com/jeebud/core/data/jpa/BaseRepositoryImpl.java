package com.jeebud.core.data.jpa;

import com.jeebud.core.data.jpa.domain.PageQuery;
import com.jeebud.core.data.jpa.domain.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public class BaseRepositoryImpl<T, D> extends SimpleJpaRepository<T, D> implements BaseRepository<T, D> {

    @PersistenceContext
    private EntityManager em;
    private JpaEntityInformation<T, ?> entityInformation;
    final private BaseRepositoryImpl exmple;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityInformation = entityInformation;
        this.em = em;
        exmple = this;
    }

    @Override
    public List<T> search(@Nullable Query query) {
        final Query param = query;
        if (query.hasSort()) {
            return this.findAll((Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> new PredicateBuilder<T>(root, cq, cb, param).builder(), getSort(query));
        } else {
            return this.findAll((Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> new PredicateBuilder<T>(root, cq, cb, param).builder());
        }
    }

    @Override
    public Page<T> search(@Nullable PageQuery pageQuery) {
        final PageQuery param = pageQuery;
        Pageable pageable = null;
        if (pageQuery.hasSort()) {
            pageable = PageRequest.of(pageQuery.getPage()-1, pageQuery.getLimit(), getSort(pageQuery));
        } else {
            pageable = PageRequest.of(pageQuery.getPage()-1, pageQuery.getLimit());
        }
        Page<T> page = this.findAll((Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> new PredicateBuilder<T>(root, cq, cb, param).builder(), pageable);
        return page;
    }

    @Override
    public long countSearch(@Nullable Query query) {
        final Query param = query;
        return this.count((Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> new PredicateBuilder<T>(root, cq, cb, param).builder());
    }

    private Sort getSort(Query query) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(query.getDirection()).orElse(Sort.Direction.ASC);
        List<String> listString = Arrays.asList(query.getSort().split(","));
        Sort sort = new Sort(direction, listString);
        return sort;
    }

}
