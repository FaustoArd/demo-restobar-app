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
@Table(name="tables_orders")
public class RestoTableOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="product_quantity")
	private Integer productQuantity;
	
	@Column(name="total_order_price")
	private BigDecimal totalOrderPrice;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", referencedColumnName = "id")
	private Product product;
	
	@OneToMany(mappedBy = "tableOrder")
	private Set<RestoTable> restoTables;
	
	
	
	
	
	
	
}
