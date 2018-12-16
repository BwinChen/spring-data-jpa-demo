package com.bwin.springdatajpademo.controller;

import com.bwin.springdatajpademo.entity.Customer;
import com.bwin.springdatajpademo.entity.CustomerProjection;
import com.bwin.springdatajpademo.repository.CustomerRepository;
import com.bwin.springdatajpademo.repository.CustomerSpecificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/customer")
@RestController
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerSpecificationRepository csr;

    /**
     * 初始化数据
     */
    @GetMapping("/index")
    public String index() {
        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
        repository.save(new Customer("Bauer", "Dessler"));
        return "success";
    }

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    public List<Customer> findAll(){
        return repository.findAll();
    }

    /**
     * 查询ID为1的数据
     */
    @GetMapping("/delete")
    public List<Customer> delete(){
        log.info("删除前数据：");
        List<Customer> customers = repository.findAll();
        for (Customer customer:customers){
            System.out.println(customer.toString());
        }
        log.info("删除ID=3数据：");
        repository.deleteById(3L);
        return repository.findAll();
    }

    /**
     * 查询ID为1的数据
     */
    @GetMapping("/findOne")
    public Customer findOne(){
        Optional<Customer> optional = repository.findById(1L);
        return optional.orElse(null);
    }

    /**
     * 查询lastName为指定用户昵称
     */
    @GetMapping("/findByLastName")
    public List<Customer> findByLastName(){
        return repository.findByLastName("Bauer");
    }

    /**
     * 查询FirstName为指定用户昵称
     */
    @GetMapping("/findByFirstName")
    public Customer findByFirstName(){
        return repository.findByFirstName("Bauer");
    }

    /**
     * 使用@Query注解方式查询
     * 查询FirstName为指定字符串
     */
    @GetMapping("/findByFirstName2")
    public Customer findByFirstName2(){
        return repository.findByFirstName2("Bauer");
    }

    /**
     * 使用@Query注解方式查询
     * 查询LastName为指定字符串
     */
    @GetMapping("/findByLastName2")
    public List<Customer> findByLastName2(){
        return repository.findByLastName2("Bauer");
    }

    /**
     * 使用@Query 注解方式查询,
     * 用@Param指定参数，匹配firstName和lastName
     */
    @GetMapping("/findByName")
    public List<Customer> findByName(){
        return repository.findByName("Bauer");
    }

    /**
     * 使用@Query注解方式查询,使用关键词like
     * 用@Param指定参数，firstName的结尾为e的字符串
     */
    @GetMapping("/findByName2")
    public List<Customer> findByName2(){
        return repository.findByName2("e");
    }

    /**
     * 使用@Query注解方式查询，模糊匹配关键字e
     */
    @GetMapping("/findByName3")
    public List<Customer> findByName3(){
        return repository.findByName3("e");
    }

    /**
     * 使用@Query注解方式查询,
     * 用@Param指定参数，匹配firstName和lastName
     */
    @GetMapping("/findByName4")
    public List<Customer> findByName4(){
        //按照ID倒序排列
        log.info("直接创建sort对象，通过排序方法和属性名");
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Customer> result = repository.findByName4("Bauer",sort);
        log.info(result.toString());

        //按照ID倒序排列
        log.info("通过Sort.Order对象创建sort对象");
        sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        result = repository.findByName4("Bauer",sort);
        log.info(result.toString());

        log.info("通过排序方法和属性List创建sort对象");
        List<String> sortProperties = new ArrayList<>();
        sortProperties.add("id");
        sortProperties.add("firstName");
        sort = new Sort(Sort.Direction.DESC,sortProperties);
        result = repository.findByName4("Bauer",sort);
        log.info(result.toString());

        log.info("通过创建Sort.Order对象的集合创建sort对象");
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"firstName"));
        return repository.findByName4("Bauer", Sort.by(orders));
    }

    /**
     * 根据FirstName进行修改
     */
    @GetMapping("/modifying")
    public int modifying(){
        return repository.setFixedFirstnameFor("Bauorx","Bauer");
    }

    /**
     * 分页
     * 应用查询提示@QueryHints，这里是在查询的适合增加了一个comment
     * 查询结果是lastName和firstName都是bauer这个值的数据
     */
    @GetMapping("/pageable")
    public List<Customer> pageable(){
        //Pageable是接口，PageRequest是接口实现
        //PageRequest的对象构造函数有多个，page是页数，初始值是0，size是查询结果的条数，后两个参数参考Sort对象的构造方法
        Pageable pageable = PageRequest.of(0,3, Sort.Direction.DESC,"id");
        Page<Customer> page = repository.findByName("bauer",pageable);
        //查询结果总行数
        log.info(page.getTotalElements() + "");
        //按照当前分页大小，总页数
        log.info(page.getTotalPages() + "");
        //按照当前页数、分页大小，查出的分页结果集合
        return page.getContent();
    }

    /**
     * find by projections
     */
    @GetMapping("/findAllProjections")
    public Collection<CustomerProjection> findAllProjections(){
        return repository.findAllProjectedBy();
    }

    @GetMapping("/spec")
    public List<Customer> specificationQuery(){
//        Specification<Customer> spec = SpecificationFactory.containsLike("lastName","bau");
        Specification<Customer> spec = (Specification<Customer>) (root, query, cb) -> cb.like(root.get("firstName"),"%bau%");
        return getCustomers(spec);
    }

    /**
     * @see <a href="https://blog.csdn.net/moshowgame/article/details/80309642">SpringDataJPA之Specification复杂查询</a>
     */
    @GetMapping("/spec2")
    public List<Customer> specificationQuery2(){
        Specification<Customer> spec = (Specification<Customer>) (root, query, cb) -> {
            Predicate condition1 = cb.like(root.get("firstName"), "%bau%");
            Predicate condition2 = cb.like(root.get("lastName"), "%bau%");
            Predicate condition = cb.or(condition1, condition2);
            query.where(condition);
            return null;
        };
        return getCustomers(spec);
    }

    private List<Customer> getCustomers(Specification<Customer> spec) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<Customer> page = csr.findAll(spec, pageable);
        log.info(page.getTotalElements() + "");
        log.info(page.getTotalPages() + "");
        return page.getContent();
    }

}
