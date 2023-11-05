package com.lord.arbam.models;

import jakarta.persistence.CascadeType;
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
@Table(name="tables_total_products")
public class RestoTableTotalProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="product_id", referencedColumnName = "id")
	private Product product;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="table_closed_id", referencedColumnName = "id")
	private RestoTableClosed tableClosed;
	
	
	

}
