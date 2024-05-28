package com.lord.arbam.model;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.Cascade;
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
import jakarta.persistence.JoinTable;
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
	private BigDecimal totalPrice;
	
	
	/*@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinTable(name="resto_table_closed_order_payment_method_junction"
	,joinColumns = {@JoinColumn(name="resto_table_closed_id", referencedColumnName = "id")}
	,inverseJoinColumns = {@JoinColumn(name="order_payment_method_id", referencedColumnName = "id")})
	private List<OrderPaymentMethod> orderPaymentMethods;*/
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name="working_day_id", referencedColumnName = "id")
	private WorkingDay workingDay;
	
	

}
