package com.jeebud.core.data.annotation;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public enum QueryType {
    //=
    EQUAL(false),
    //bewteen
    BEWTEEN(false),
    //<
    LESS_THAN(false),
    //<=
    LESS_THAN_EQUAL(false),
    //>
    GREATEROR_THAN(false),
    //>=
    GREATEROR_THAN_EQUAL(false),
    //!=
    NOT_EQUAL(false),
    // is null
    IS_NULL(true),
    //is not null
    IS_NOT_NULL(true),
    //右like
    RIGHT_LIKE(false),
    //左like
    LEFT_LIKE(false),
    //左右like
    LIKE(false),
    //like
    NOT_LIKE(false),
    //not 右like
    NOT_RIGHT_LIKE(false),
    //not 左like
    NOT_LEFT_LIKE(false),
    //in
    IN(false),
    //not in
    NOT_IN(false);
    /**
     * 是否可以为空
     */
    private boolean isCanBeNull;

    private QueryType(boolean isCanBeNull) {
        this.isCanBeNull = isCanBeNull;
    }

    public boolean isNotCanBeNull() {
        return !this.isCanBeNull;
    }


    public boolean isCanBeNull() {
        return this.isCanBeNull;
    }
}
