package com.filter.jwtauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<com.filter.jwtauthentication.model.Authority, Integer> {
}
