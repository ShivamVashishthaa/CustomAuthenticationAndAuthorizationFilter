package com.filter.jwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String email;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        if (authority == null) return;

        if (!this.authorities.contains(authority)) {
            this.authorities.add(authority);
        }

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
}
