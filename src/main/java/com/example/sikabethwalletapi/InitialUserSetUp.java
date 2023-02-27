//package com.example.sikabethwalletapi;
//
//import com.example.sikabethwalletapi.enums.Authorities;
//import com.example.sikabethwalletapi.enums.Roles;
//import com.example.sikabethwalletapi.enums.Status;
//import com.example.sikabethwalletapi.model.Authority;
//import com.example.sikabethwalletapi.model.Role;
//import com.example.sikabethwalletapi.model.User;
//import com.example.sikabethwalletapi.repository.AuthorityRepository;
//import com.example.sikabethwalletapi.repository.RoleRepository;
//import com.example.sikabethwalletapi.repository.UserRepository;
//import com.example.sikabethwalletapi.util.AmazonSES;
//import com.example.sikabethwalletapi.util.AppUtil;
//import com.example.sikabethwalletapi.util.LocalStorage;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
//@Component
//@AllArgsConstructor
//public class InitialUserSetUp {
//
//    @Value(value = "${user.email.activate}")
//    private String USER_EMAIL_ACTIVATE;
//
//    private final AuthorityRepository authorityRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder bCryptPasswordEncoder;
//    private final UserRepository userRepository;
//    private final LocalStorage localStorage;
//    private final AppUtil util;
//    private final AmazonSES amazonSES;
//
//
//    @EventListener
//    @Transactional
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        System.out.println("From Application ready event....");
//
//        Authority readAuthority = createAuthority(Authorities.READ_AUTHORITY.name());
//        Authority writeAuthority = createAuthority(Authorities.WRITE_AUTHORITY.name());
//        Authority privilegeAuthority = createAuthority(Authorities.PRIVILEGE_AUTHORITY.name());
//        Authority deleteAuthority = createAuthority(Authorities.DELETE_AUTHORITY.name());
//
//        createRole(Roles.ROLE_USER.name(), Collections.singletonList(readAuthority));
//        createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority));
//        createRole(Roles.ROLE_PRIVILEGE.name(), List.of(privilegeAuthority));
//        Role roleSuperAdmin = createRole(Roles.ROLE_SUPER_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, privilegeAuthority, deleteAuthority));
//
//        if (roleSuperAdmin == null) return;
//
//        User superAdminUser = User.builder()
//                .uuid(UUID.randomUUID().toString())
//                .firstName("super")
//                .lastName("super")
//                .email("sikabeth2supply@gmail.com")
//                .status(Status.INACTIVE)
//                .encryptedPassword(bCryptPasswordEncoder.encode("1234567"))
//                .lastLoginDate(new Date())
//                .country("Nigeria")
//                .state("Nigeria")
//                .homeAddress("Nigeria")
//                .phoneNumber("Nigeria")
//                .build();
//
//        superAdminUser.setRoles(List.of(roleSuperAdmin));
//
//        User storedSuperUser = userRepository.findByEmail("sikabeth2supply@gmail.com").orElse(null);
//        if(storedSuperUser == null) {
//            userRepository.save(superAdminUser);
//            String token = util.generateSerialNumber("A");
//            localStorage.save(USER_EMAIL_ACTIVATE + "sikabeth2supply@gmail.com", token, 43200000);
//
//            amazonSES.verifyEmail(token, "sikabeth2supply@gmail.com");
//        }
//
//    }
//    @Transactional
//    public Authority createAuthority(String authorityName) {
//        Authority authority = authorityRepository.findByName(authorityName);
//        if (authority == null) {
//            authority = new Authority(authorityName);
//            authorityRepository.save(authority);
//        }
//        return authority;
//    }
//
//    @Transactional
//    public Role createRole(String roleName, List<Authority> authorities) {
//        Role role = roleRepository.findByName(roleName);
//        if (role == null) {
//            role = new Role(roleName);
//            role.setAuthorities(authorities);
//            roleRepository.save(role);
//        }
//        return role;
//    }
//
//}
