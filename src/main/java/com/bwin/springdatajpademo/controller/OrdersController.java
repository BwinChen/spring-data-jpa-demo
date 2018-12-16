package com.bwin.springdatajpademo.controller;

import com.bwin.springdatajpademo.entity.Customer;
import com.bwin.springdatajpademo.entity.Orders;
import com.bwin.springdatajpademo.repository.OrdersRepository;
import com.bwin.springdatajpademo.util.SpecificationFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
@AllArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrdersController {
    
    private OrdersRepository ordersRepository;

    /**
     * 内连接查询
     */
    @GetMapping("/q1")
    public List<Orders> specification1() {
        //根据查询结果，声明返回值对象，这里要查询用户的订单列表，所以声明返回对象为MyOrder
        //Root<X>  根查询，默认与声明相同
        Specification<Orders> spec = (Specification<Orders>) (root, query, cb) -> {
            //声明并创建MyOrder的CriteriaQuery对象
            CriteriaQuery<Orders> criteriaQuery = cb.createQuery(Orders.class);
            //连接的时候，要以声明的根查询对象（这里是root，也可以自己创建）进行join
            //Join<Z,X>是Join生成的对象，这里的Z是被连接的对象，X是目标对象，
            //  连接的属性字段是被连接的对象在目标对象的属性，这里是我们在MyOrder内声明的customer
            //join的第二个参数是可选的，默认是JoinType.INNER(内连接 inner join)，也可以是JoinType.LEFT（左外连接 left join）
            Join<Customer, Orders> myOrderJoin = root.join("customer", JoinType.INNER);
            //用CriteriaQuery对象拼接查询条件，这里只增加了一个查询条件，cId=1
            criteriaQuery.select(myOrderJoin).where(cb.equal(root.get("customer").get("id"), 1));
            //通过getRestriction获得Predicate对象
            //返回对象
            return criteriaQuery.getRestriction();
        };
        return resultPrint(spec);
    }

    /**
     * 增加查询条件，关联的对象Customer的对象值
     */
    @GetMapping("/q2")
    public List<Orders> specification2() {
        Specification<Orders> spec = (Specification<Orders>) (root, query, cb) -> {
            CriteriaQuery<Orders> criteriaQuery = cb.createQuery(Orders.class);
            Join<Customer, Orders> myOrderJoin = root.join("customer");
            criteriaQuery.select(myOrderJoin)
                    .where(
                            cb.equal(root.get("customer").get("id"), 1),//cId=1
                            cb.equal(root.get("customer").get("firstName"), "Bauorx")//对象customer的firstName=Jack
                    );
            return criteriaQuery.getRestriction();
        };
        return resultPrint(spec);
    }

    /**
     * in的条件查询
     * 需要将对应的结果集以root.get("attributeName").in(Object.. values)的方式传入
     * values支持多个参数，支持对象（Object），表达式Expression<?>，集合Collection以及Expression<Collection<?>>
     */
    @GetMapping("/q3")
    public List<Orders> specification3() {
        Specification<Orders> spec = (Specification<Orders>) (root, query, cb) -> {
            CriteriaQuery<Orders> criteriaQuery = cb.createQuery(Orders.class);
            Join<Customer, Orders> myOrderJoin = root.join("customer");
            criteriaQuery.select(myOrderJoin)
                    .where(
                            cb.equal(root.get("customer").get("id"), 1)
                            , root.get("id").in(1, 2, 4)
                    );

            return criteriaQuery.getRestriction();
        };
        return resultPrint(spec);
    }

    /**
     * 左外链接查询，对比inner join，
     * 这里只是改了一个参数，将JoinType.INNER改成JoinType.LEFT
     * <p>
     * 注意，当前示例不支持JoinType.RIGHT，用的比较少，没有探究
     */
    @GetMapping("/q4")
    public List<Orders> specification4() {
        Specification<Orders> spec = (Specification<Orders>) (root, query, cb) -> {
            CriteriaQuery<Orders> criteriaQuery = cb.createQuery(Orders.class);
            Join<Customer, Orders> myOrderJoin = root.join("customer", JoinType.LEFT);
            criteriaQuery.select(myOrderJoin).where(cb.equal(root.get("customer").get("id"), 1));
            return criteriaQuery.getRestriction();
        };
        return resultPrint(spec);
    }

    @GetMapping("/q5")
    public List<Orders> specification5() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(4L);
        Specification<Orders> spec = SpecificationFactory.in("id", list);
        return resultPrint(spec);
    }

    private List<Orders> resultPrint(Specification<Orders> spec) {
        //分页查询
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");
        //查询的分页结果
        Page<Orders> page = ordersRepository.findAll(spec, pageable);
        return page.getContent();
    }

}
