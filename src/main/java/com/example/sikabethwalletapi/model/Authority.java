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
//import javax.persistence.Id;
//import java.util.Collection;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Document("authorities")
//public class Authority extends Base {
//
//    private static final long serialVersionUID  = -2475195685918239575L;
//
//    @Id
//    private String id;
//    @Indexed(unique = true)
//    private String name;
//
//    @DBRef(lazy = true)
//    private Collection<Role> roles;
//
//    public Authority(String name) {
//        this.name = name;
//    }
//
//}
