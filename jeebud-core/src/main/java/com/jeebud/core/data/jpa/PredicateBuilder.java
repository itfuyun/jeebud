package com.jeebud.core.data.jpa;

import com.jeebud.common.util.ObjectUtils;
import com.jeebud.common.util.StringUtils;
import com.jeebud.core.data.annotation.QueryField;
import com.jeebud.core.data.annotation.QueryType;
import com.jeebud.core.data.jpa.domain.Query;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public class PredicateBuilder<T> {

    private Root<T> root;
    private CriteriaQuery<?> query;
    private CriteriaBuilder cb;
    private Query queryParam;
    private static final String POINT_MARK = ".";

    public PredicateBuilder(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb, Query queryParam) {
        this.root = root;
        this.query = cq;
        this.cb = cb;
        this.queryParam = queryParam;
    }

    public Predicate builder() {
        return getPredicate(root, query, cb, queryParam);
    }

    protected Predicate getPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb,
                                     Query queryParam) {
        List<Predicate> predicates = new ArrayList<>();
        Field[] fields = queryParam.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String queryFiled = null;
            QueryType queryType = null;
            Object value = null;
            QueryField annotation = field.getAnnotation(QueryField.class);
            if (annotation == null) {
                continue;
            }
            if(ObjectUtils.isNotNull(annotation.name())){
                queryFiled = annotation.name();
            }else {
                queryFiled = field.getName();
            }
            queryType = annotation.type();
            try {
                value = field.get(queryParam);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isNull(value) && queryType.isNotCanBeNull()) {
                log.debug("查询类型：" + queryType + "不允许为空。");
                continue;
            }
            buildPredicate(queryType, predicates, queryFiled, value);
        }
        if (predicates.size() == 0) {
            return cb.and();
        }
        Predicate[] t = new Predicate[predicates.size()];
        Predicate[] result = predicates.toArray(t);
        return cb.and(result);
    }

    private void buildPredicate(QueryType queryType, List<Predicate> predicates, String queryFiled, Object value) {
        Predicate predicate = null;
        switch (queryType) {
            case EQUAL:
                if (value == null || StringUtils.isEmpty(value.toString())) {
                    break;
                }
                predicates.add(equal(queryFiled, value));
                break;
            case BEWTEEN:
                predicate = between(queryFiled, value);
                if (ObjectUtils.isNotNull(predicate)) {
                    predicates.add(predicate);
                }
                break;
            case LESS_THAN:
                predicate = lessThan(queryFiled, value);
                if (ObjectUtils.isNotNull(predicate)) {
                    predicates.add(predicate);
                }
                break;
            case LESS_THAN_EQUAL:
                predicate = lessThanEqual(queryFiled, value);
                if (ObjectUtils.isNotNull(predicate)) {
                    predicates.add(predicate);
                }
                break;
            case GREATEROR_THAN:
                predicate = greaterThan(queryFiled, value);
                if (ObjectUtils.isNotNull(predicate)) {
                    predicates.add(predicate);
                }
                break;
            case GREATEROR_THAN_EQUAL:
                predicate = greaterThanEqual(queryFiled, value);
                if (ObjectUtils.isNotNull(predicate)) {
                    predicates.add(predicate);
                }
                break;
            case NOT_EQUAL:
                predicates.add(notEqual(queryFiled, value));
                break;
            case IS_NULL:
                predicates.add(isNull(queryFiled));
                break;
            case IS_NOT_NULL:
                predicates.add(isNotNull(queryFiled));
                break;
            case LEFT_LIKE:
                predicates.add(isLeftLike(queryFiled, value));
                break;
            case RIGHT_LIKE:
                predicates.add(isRightLike(queryFiled, value));
                break;
            case LIKE:
                predicates.add(isLike(queryFiled, value));
                break;
            case NOT_LIKE:
                predicates.add(isNotLike(queryFiled, value));
                break;
            case NOT_LEFT_LIKE:
                predicates.add(isNotLeftLike(queryFiled, value));
                break;
            case NOT_RIGHT_LIKE:
                predicates.add(isNotRightLike(queryFiled, value));
                break;
            case IN:
                predicates.add(in(queryFiled, value));
                break;
            case NOT_IN:
                predicates.add(cb.not(in(queryFiled, value)));
                break;
            default:
                break;
        }
    }

    private Predicate equal(String queryFiled, Object value) {
        Path<Object> equal = getRootByQueryFiled(queryFiled, root);
        return cb.equal(equal, value);
    }

    private Predicate between(String queryFiled, Object value) {
        Path<Comparable> between = getRootByQueryFiledComparable(queryFiled, root);
        QueryBetween queryBetween = null;
        if (value instanceof QueryBetween) {
            queryBetween = (QueryBetween) value;
        } else {
            return null;
        }
        return cb.between(between, queryBetween.after, queryBetween.before);
    }

    private Predicate lessThan(String queryFiled, Object value) {
        QueryBetween queryBetween = null;
        Path<Comparable> lessThan = getRootByQueryFiledComparable(queryFiled, root);
        if (value instanceof QueryBetween) {
            queryBetween = (QueryBetween) value;
        } else {
            return null;
        }
        return cb.lessThan(lessThan, queryBetween.after);
    }

    private Predicate lessThanEqual(String queryFiled, Object value) {
        QueryBetween queryBetween = null;
        Path<Comparable> lessThanOrEqualTo = getRootByQueryFiledComparable(queryFiled, root);
        if (value instanceof QueryBetween) {
            queryBetween = (QueryBetween) value;
        } else {
            return null;
        }
        return cb.lessThanOrEqualTo(lessThanOrEqualTo, queryBetween.after);
    }

    private Predicate greaterThan(String queryFiled, Object value) {
        QueryBetween queryBetween = null;
        Path<Comparable> greaterThan = getRootByQueryFiledComparable(queryFiled, root);
        if (value instanceof QueryBetween) {
            queryBetween = (QueryBetween) value;
        } else {
            return null;
        }
        return cb.greaterThan(greaterThan, queryBetween.after);
    }

    private Predicate greaterThanEqual(String queryFiled, Object value) {
        QueryBetween queryBetween = null;
        Path<Comparable> greaterThanOrEqualTo = getRootByQueryFiledComparable(queryFiled, root);
        if (value instanceof QueryBetween) {
            queryBetween = (QueryBetween) value;
        } else {
            return null;
        }
        return cb.lessThanOrEqualTo(greaterThanOrEqualTo, queryBetween.after);
    }

    private Predicate notEqual(String queryFiled, Object value) {
        Path<Object> notEqual = getRootByQueryFiled(queryFiled, root);
        return cb.notEqual(notEqual, value);
    }

    private Predicate isNull(String queryFiled) {
        Path<Object> isNull = getRootByQueryFiled(queryFiled, root);
        return cb.isNull(isNull);
    }

    private Predicate isNotNull(String queryFiled) {
        Path<Object> isNull = getRootByQueryFiled(queryFiled, root);
        return cb.isNotNull(isNull);
    }

    private Predicate isLeftLike(String queryFiled, Object value) {
        Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
        return cb.like(leftLike, "%" + value.toString());
    }

    private Predicate isRightLike(String queryFiled, Object value) {
        Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
        return cb.like(leftLike, value.toString() + "%");
    }

    private Predicate isLike(String queryFiled, Object value) {
        Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
        return cb.like(leftLike, "%" + value.toString() + "%");
    }

    private Predicate isNotLeftLike(String queryFiled, Object value) {
        Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
        return cb.notLike(leftLike, "%" + value.toString());
    }

    private Predicate isNotRightLike(String queryFiled, Object value) {
        Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
        return cb.notLike(leftLike, value.toString() + "%");
    }

    private Predicate isNotLike(String queryFiled, Object value) {
        Path<String> leftLike = getRootByQueryFiledString(queryFiled, root);
        return cb.notLike(leftLike, "%" + value.toString() + "%");
    }

    private Predicate in(String queryFiled, Object value) {
        Path<Object> in = getRootByQueryFiled(queryFiled, root);
        CriteriaBuilder.In ins = cb.in(in);
        if (value instanceof Collection) {
            List inList = (List) value;
            for (Object object : inList) {
                ins.value(object);
            }
        } else if (value.getClass().isArray()) {
            Object[] objects = (Object[]) value;
            for (Object obj : objects) {
                ins.value(obj);
            }

        } else if (value instanceof String) {
            String[] strArry = value.toString().split(",");
            for (String str : strArry) {
                ins.value(str);
            }
        }
        return ins;
    }

    protected Path<Object> getRootByQueryFiled(String queryFiled, Root<T> root) {
        if (queryFiled.indexOf(POINT_MARK) < 0) {
            return root.get(queryFiled);
        } else {
            return getRootByQueryFiled(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), root.get(queryFiled.substring(0, queryFiled.indexOf("."))));
        }
    }

    protected Path<Object> getRootByQueryFiled(String queryFiled, Path<Object> path) {
        if (queryFiled.indexOf(POINT_MARK) < 0) {
            return path.get(queryFiled);
        } else {
            return getRootByQueryFiled(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), path.get(queryFiled.substring(0, queryFiled.indexOf("."))));
        }
    }

    protected Path<String> getRootByQueryFiledString(String queryFiled, Root<T> root) {
        if (queryFiled.indexOf(POINT_MARK) < 0) {
            return root.get(queryFiled);
        } else {
            return getRootByQueryFiledString(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), root.get(queryFiled.substring(0, queryFiled.indexOf("."))));
        }
    }

    protected Path<String> getRootByQueryFiledString(String queryFiled, Path<Object> path) {
        if (queryFiled.indexOf(POINT_MARK) < 0) {
            return path.get(queryFiled);
        } else {
            return getRootByQueryFiledString(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), path.get(queryFiled.substring(0, queryFiled.indexOf("."))));
        }
    }

    protected Path<Comparable> getRootByQueryFiledComparable(String queryFiled, Root<T> root) {
        if (queryFiled.indexOf(POINT_MARK) < 0) {
            return root.get(queryFiled);
        } else {
            return getRootByQueryFiledComparable(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), root.get(queryFiled.substring(0, queryFiled.indexOf("."))));
        }
    }

    protected Path<Comparable> getRootByQueryFiledComparable(String queryFiled, Path<Object> path) {
        if (queryFiled.indexOf(POINT_MARK) < 0) {
            return path.get(queryFiled);
        } else {
            return getRootByQueryFiledComparable(queryFiled.substring(queryFiled.indexOf(".") + 1, queryFiled.length()), path.get(queryFiled.substring(0, queryFiled.indexOf("."))));
        }
    }
}
