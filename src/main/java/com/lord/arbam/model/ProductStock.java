package com.lord.arbam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name="product_stock")
public class ProductStock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO ) 
	private Long id;
	
	
	@Column(name="product_quantity", nullable = false)
	private Integer productStock;
	
	@OneToOne(mappedBy = "productStock")
	private Product product;
	
	public ProductStock(Integer productStock) {
		this.productStock = productStock;
	}
}
