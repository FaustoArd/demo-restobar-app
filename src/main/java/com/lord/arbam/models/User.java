package com.lord.arbam.models;



import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	@Column(name="name", nullable = false)
	private String name;

	@NonNull
	@Column(name="lastname", nullable = false)
	private String lastname;
	
	@NonNull
	@Column(name="username", nullable = false)
	private String username;
	
	@Nonnull
	@Column(name="email", nullable =  false)
	private String email;
	
	@NonNull
	@Column(name="password", nullable =  false)
	private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name="user_role_junction", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
	private Set<Role> authorities;
	
	
}
