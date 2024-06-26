package com.lord.arbam.model;

import java.math.BigDecimal;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="tables_order_closed")
public class RestoTableOrderClosed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="product_quantity")
	private Integer productQuantity;
	
	@Column(name="total_order_price")
	private BigDecimal totalOrderPrice;
	
	@Column(name="product_name")
	private String productName;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="order_payment_method_id", referencedColumnName = "id")
	private OrderPaymentMethod orderPaymentMethod;
	
	
	

}
