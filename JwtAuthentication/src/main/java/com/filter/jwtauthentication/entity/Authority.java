package com.filter.jwtauthentication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private String id;
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users = new HashSet<>();


//    If want to store authority in db
//    // 🔥 ADD helper
//    public void addUser(User user) {
//        if (user == null) return;
//
//        if (this.users == null) {
//            this.users = new HashSet<>();
//        }
//
//        if (!this.users.contains(user)) {
//            this.users.add(user);
//            user.addAuthority(this); // 🔥 sync both sides
//        }
//    }
//
//    // 🔥 REMOVE helper
//    public void removeUser(User user) {
//        if (user == null || this.users == null) return;
//
//        if (this.users.remove(user)) {
//            user.removeAuthority(this);
//        }
//    }

}
