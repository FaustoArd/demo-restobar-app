package com.lord.arbam.models;

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
@Table(name="ingredient")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="ingredient_name", nullable = false)
	private String ingredientName;
	
	@Column(name="ingredient_amount")
	private Integer ingredientAmount;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="ingredient_category_id", referencedColumnName = "id")
	private IngredientCategory ingredientCategory;
	
	@DateTimeFormat
	@Column(name="expiration_date")
	private Calendar expirationDate;
	
	
	
	
	
	
}
