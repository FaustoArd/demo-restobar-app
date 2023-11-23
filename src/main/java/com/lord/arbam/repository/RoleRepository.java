package com.lord.arbam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findByAuthority(String authority);
}
