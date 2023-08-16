package com.hisujung.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hisujung.web.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, unique = true)
    private String email;

    private String userName;

    private String password;

    private String department1;

    private String department2;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "member_chatroom",
//            joinColumns = @JoinColumn(name = "member_id"),
//            inverseJoinColumns = @JoinColumn(name = "chatroom_id")
//    )
//    private Set<ChatRoom> chatRooms = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<ChatingJoinInfo> joinList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Portfolio> portfolioList = new ArrayList<>();

    public boolean checkPassword(PasswordEncoder passwordEncoder, String inputPassword) {
        return passwordEncoder.matches(inputPassword, this.password);
    }

    public String getUsername() {return userName;}

    public void addUserAuthority() {
        this.role = Role.USER;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    @Builder
    public Member(String email, String userName, String password, String department1, String department2, Role role) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.department1 = department1;
        this.department2 = department2;
        this.role = role;
    }
}
//
//package HiSujung.HiSujung_Backend2.entity;
//
//import HiSujung.HiSujung_Backend2.BaseTimeEntity;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//@Builder
//@Entity
//public class Member extends BaseTimeEntity {
//
//    @Id @Column(name = "member_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 45, unique = true)
//    private String email;
//
//    private String userName;
//
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    public void addUserAuthority() {
//        this.role = Role.USER;
//    }
//
//    public void encodePassword(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(password);
//    }
//}
