package com.lord.arbam.models;

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
	private Double totalPrice;
	
	@Column(name="payment_method")
	private String paymentMethod;
	
	

}
