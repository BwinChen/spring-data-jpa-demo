package com.bwin.springdatajpademo;

import com.bwin.springdatajpademo.entity.Orders;
import com.bwin.springdatajpademo.entity.Role;
import com.bwin.springdatajpademo.entity.User;
import com.bwin.springdatajpademo.mapper.OrdersMapper;
import com.bwin.springdatajpademo.repository.OrdersRepository;
import com.bwin.springdatajpademo.repository.RoleRepository;
import com.bwin.springdatajpademo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataJpaDemoApplicationTests {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * 测试spring data jpa与mybatis共存
     */
    @Test
    public void testMybatis() {
        Orders order = ordersMapper.findById(1L);
        order = ordersRepository.findById(2L).orElse(null);
    }

    @Transactional
    @Test
    public void testUser() {
        User user1 = new User();
        user1.setName("user1");
        user1.setPassword("password1");
        Role role1 = new Role();
        role1.setName("role1");
        Role role2 = new Role();
        role2.setName("role2");
        user1.setRoles(Arrays.asList(role1, role2));
        userRepository.save(user1);
        log.info(userRepository.findAll().toString());
    }

}
