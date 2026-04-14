package com.filter.jwtauthentication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    private String email;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        if (authority == null) return;

        if (!this.authorities.contains(authority)) {
            this.authorities.add(authority);
        }}

//    If want to add authrity in  db
//    // 🔥 ADD helper
//    public void addAuthority(Authority authority) {
//        if (authority == null) return;
//
//        if (this.authorities == null) {
//            this.authorities = new HashSet<>();
//        }
//
//        if (!this.authorities.contains(authority)) {
//            this.authorities.add(authority);
//            authority.addUser(this); // 🔥 sync both sides
//        }
//    }
//
//    // 🔥 REMOVE helper
//    public void removeAuthority(Authority authority) {
//        if (authority == null || this.authorities == null) return;
//
//        if (this.authorities.remove(authority)) {
//            authority.removeUser(this);
//        }
//        }


}
