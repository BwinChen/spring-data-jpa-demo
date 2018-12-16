package com.bwin.springdatajpademo.repository;

import com.bwin.springdatajpademo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public interface CustomerSpecificationRepository extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {
}
