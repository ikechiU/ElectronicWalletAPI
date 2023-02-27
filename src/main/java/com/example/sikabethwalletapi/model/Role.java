//package com.example.sikabethwalletapi.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import javax.persistence.*;
//import java.util.Collection;
//
//
//@Getter
//@Setter
//@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Document("roles")
//public class Role {
//
//    private static final long serialVersionUID  = -2475195685918239586L;
//
//    @Id
//    private String id;
//    @Indexed(unique = true)
//    private String name;
//
//    @DBRef(lazy = true)
//    private Collection<User> users;
//
//    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
//    @JoinTable(name = "roles_authorities",
//            joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
//    private Collection<Authority> authorities;
//
//    public Role(String name) {
//        this.name = name;
//    }
//
//}
