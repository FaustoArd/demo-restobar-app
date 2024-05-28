package com.lord.arbam.model;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name="order_payment_method")
public class OrderPaymentMethod {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="payment_method_id", referencedColumnName = "id")
	private PaymentMethod paymentMethod;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="resto_table_closed_id", referencedColumnName = "id")
	private RestoTableClosed restoTableClosed;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name="orders_order_payment_method_junction",
	joinColumns = {@JoinColumn(name="order_payment_method_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn
			(name="resto_table_order_closed_id", referencedColumnName = "id")})
	private List<RestoTableOrderClosed> orders;
	
	private BigDecimal paymentTotal;
	
	
	
	

}
