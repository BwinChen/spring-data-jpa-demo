package com.bwin.springdatajpademo.repository;

import com.bwin.springdatajpademo.entity.Orders;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2017/7/17 0017.
 */
public interface OrdersRepository extends JpaSpecificationExecutor<Orders>, CrudRepository<Orders, Long> {
}
