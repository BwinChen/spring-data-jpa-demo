package com.bwin.springdatajpademo.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;

}
