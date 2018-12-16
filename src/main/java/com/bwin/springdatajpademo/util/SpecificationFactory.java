package com.bwin.springdatajpademo.util;

import org.springframework.data.jpa.domain.Specification;
import java.util.Collection;

/**
 * Created by Administrator on 2017/7/13 0013.
 */
public final class SpecificationFactory {

    /**
     * 模糊查询，匹配对应字段
     */
    public static Specification containsLike(String attribute, String value) {
        return (root, query, cb) -> cb.like(root.get(attribute), "%" + value + "%");
    }

    /**
     * 某字段的值等于value的查询条件
     */
    public static Specification equal(String attribute, Object value) {
        return (root, query, cb) -> cb.equal(root.get(attribute), value);
    }

    /**
     * 获取对应属性的值所在区间
     */
    public static Specification isBetween(String attribute, int min, int max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }

    public static Specification isBetween(String attribute, double min, double max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }

    /**
     * 通过属性名和集合实现in查询
     */
    public static Specification in(String attribute, Collection c) {
        return (root, query, cb) -> root.get(attribute).in(c);
    }

}
