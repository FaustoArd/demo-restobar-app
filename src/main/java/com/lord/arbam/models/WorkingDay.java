package com.lord.arbam.models;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name="total_employee_salary")
	private BigDecimal totalEmployeeSalary;
	

}
