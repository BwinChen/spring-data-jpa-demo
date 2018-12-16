package com.bwin.springdatajpademo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 多对一
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private BigDecimal total;
    //可加可不加
    private Long customerId;
    //实体映射重复列必须设置：insertable = false,updatable = false
    @ManyToOne
    @JoinColumn(name = "customerId", insertable = false, updatable = false)
    private Customer customer;

}
