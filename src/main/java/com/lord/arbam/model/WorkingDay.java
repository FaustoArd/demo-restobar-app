package com.lord.arbam.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@Column(name="date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Calendar date;
	
	@Column(name="total_start_cash")
	private BigDecimal totalStartCash;
	
	@Column(name="total_working_day")
	private BigDecimal totalWorkingDay;
	
	@Column(name="total_cash")
	private BigDecimal totalCash;
	
	@Column(name="total_cash_discounted")
	private BigDecimal totalWorkingDayWithDiscount;
	
	@Column(name="total_debit")
	private BigDecimal totalDebit;
	
	@Column(name="total_credit")
	private BigDecimal totalCredit;
	
	@Column(name="total_total_mp")
	private BigDecimal totalMP;
	
	@Column(name="total_transf")
	private BigDecimal totalTransf;
	
	@Column(name="total_employee_salary")
	private BigDecimal totalEmployeeSalary;
	
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name="employee_working_day_junction", joinColumns = 
	@JoinColumn(name="working_day_id", referencedColumnName = "id"), inverseJoinColumns =
	@JoinColumn(name="employee_id", referencedColumnName = "id",unique = false))
	private List<Employee> employees;
	
	@Column(name="day_started")
	private boolean dayStarted;
	
	
	

}
