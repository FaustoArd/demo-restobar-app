package com.lord.arbam.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="users")
public class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="name", nullable = false )
	private String name;

	
	@Column(name="lastname", nullable = false)
	private String lastname;
	
	
	@Column(name="username", nullable = false, unique = true)
	private String username;
	
	
	@Column(name="email", nullable =  false, unique = true)
	private String email;
	
	
	@Column(name="password", nullable =  false)
	private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinTable(name="user_role_junction", joinColumns = { @JoinColumn(name="user_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")})
	private Set<Role> authorities;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	
}
