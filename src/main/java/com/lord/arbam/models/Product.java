package com.lord.arbam.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(nullable = false,length = 150)
	private String productName;
	
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name="category_id", referencedColumnName = "id", nullable = false)
	private ProductCategory category;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name="stock_id", referencedColumnName = "id")
	private ProductStock productStock;
	
	
	@OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name="price_id", referencedColumnName = "id",nullable = false)
	private ProductPrice productPrice;
	
	@Column(name="mix")
	private boolean mixed;
	
	}
