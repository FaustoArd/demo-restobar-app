package com.lord.arbam.model;

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
import jakarta.persistence.ManyToOne;
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
@Table(name="resto_tables")
public class RestoTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="table_number")
	private Integer tableNumber;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name="employee_id", referencedColumnName = "id")
	private Employee employee;
	
	@Column(name="total_table_price")
	private BigDecimal totalTablePrice;
	
	@OneToMany( mappedBy = "restoTable")
	private Set<RestoTableOrder> tableOrders;
	
	@Column(name="payment_method")
	private String paymentMethod;
	
	@Column(name="open")
	private boolean open;

}
