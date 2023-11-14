package com.lord.arbam.models;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="tables_closed")
public class RestoTableClosed {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="table_number")
	private Integer tableNumber;
	
	@Column(name="employee_name")
	private String employeeName;
	
	@Column(name="total_table_price")
	private BigDecimal totalPrice;
	
	@Column(name="payment_method")
	private String paymentMethod;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="working_day_id", referencedColumnName = "id")
	private WorkingDay workingDay;
	
	

}
