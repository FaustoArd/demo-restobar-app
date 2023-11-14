package com.lord.arbam.models;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="working_day")
public class WorkingDay {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="total_start_cash")
	private Double totalStartCash;
	
	@Column(name="total_working_day")
	private BigDecimal totalWorkingDay;
	
	@Column(name="total_post_employee_salary")
	private BigDecimal totalPostEmployeeSalary;
	
	@Column(name="total_cash")
	private BigDecimal totalCash;
	
	@Column(name="total_debit")
	private BigDecimal totalDebit;
	
	@Column(name="total_transf")
	private BigDecimal totalTransf;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name="waitress_working_day_junction", joinColumns = 
	@JoinColumn(name="working_day_id", referencedColumnName = "id"), inverseJoinColumns =
	@JoinColumn(name="waitress_id", referencedColumnName = "id"))
	private Set<Employee> waitresses;
	
	@Column(name="cashierName")
	private String cashierName;
	
	@Column(name="total_waitress_salary")
	private BigDecimal totalWaitressSalary;
	
	@Column(name="total_cashier_salary")
	private BigDecimal totalCashierSalary;
	

}
