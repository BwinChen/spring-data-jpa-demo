package com.bwin.springdatajpademo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bwin.springdatajpademo.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    @Select("select id, code, total, customer_id from orders where id = #{id}")
    Orders findById(@Param("id")Long id);

}
