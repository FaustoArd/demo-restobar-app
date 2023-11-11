package com.lord.arbam.models;

import java.math.BigDecimal;
import java.util.Calendar;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
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
@Table(name="resto_tables")
public class RestoTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	@Column(name="table_number")
	private Integer tableNumber;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="employee_id", referencedColumnName = "id")
	private Employee employee;
	
	@Column(name="total_table_price")
	private BigDecimal totalTablePrice;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinTable(name="table_orders_junction", joinColumns = @JoinColumn(name="resto_table_id"), inverseJoinColumns = @JoinColumn(name="table_order_id"))
	private RestoTableOrder tableOrder;
	
	@DateTimeFormat
	@Column(name="close_time")
	private Calendar closeTime;
	
	@Column(name="open")
	private boolean open;

}
