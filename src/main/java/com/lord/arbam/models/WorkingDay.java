package com.lord.arbam.models;

import java.math.BigDecimal;
import java.util.List;
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
	private BigDecimal totalStartCash;
	
	@Column(name="total_working_day")
	private BigDecimal totalWorkingDay;
	
	@Column(name="total_cash")
	private BigDecimal totalCash;
	
	@Column(name="total_cash_discounted")
	private BigDecimal totalCashDiscounted;
	
	@Column(name="total_debit")
	private BigDecimal totalDebit;
	
	@Column(name="total_transf")
	private BigDecimal totalTransf;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name="waitress_working_day_junction", joinColumns = 
	@JoinColumn(name="working_day_id", referencedColumnName = "id"), inverseJoinColumns =
	@JoinColumn(name="waitress_id", referencedColumnName = "id"))
	private List<Employee> waitresses;
	
	@Column(name="cashierName")
	private String cashierName;
	
	@Column(name="total_waitress_salary")
	private BigDecimal totalWaitressSalary;
	
	@Column(name="total_cashier_salary")
	private BigDecimal totalCashierSalary;
	
	@Column(name="total_chef_salary")
	private BigDecimal totalChefSalary;
	
	@Column(name="total_helper_salary")
	private BigDecimal totalHelperSalary;
	
	@Column(name="total_dish_washer_salary")
	private BigDecimal totalDishWasherSalary;
	
	@Column(name="day_started")
	private boolean dayStarted;
	
	
	

}
