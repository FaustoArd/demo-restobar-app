package com.lord.arbam.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lord.arbam.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findByAuthority(String authority);
}
