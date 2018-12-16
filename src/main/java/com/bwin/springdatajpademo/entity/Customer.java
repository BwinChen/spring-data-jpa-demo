package com.bwin.springdatajpademo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQuery;
import javax.persistence.*;

@NoArgsConstructor
@Data
@NamedQuery(name="Customer.findByFirstName",query = "select c from Customer c where c.firstName = ?1")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    //一对多，一个客户对应多个订单，关联的字段是订单里的cId字段
//    @OneToMany
//    @JoinColumn(name = "customerId")
//    private List<Orders> orders;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
