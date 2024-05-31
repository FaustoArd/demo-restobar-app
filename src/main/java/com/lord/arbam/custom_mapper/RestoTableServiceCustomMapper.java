package com.lord.arbam.custom_mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.lord.arbam.dto.RestoTableOrderClosedDto;
import com.lord.arbam.model.Employee;
import com.lord.arbam.model.RestoTable;
import com.lord.arbam.model.RestoTableClosed;
import com.lord.arbam.model.RestoTableOrderClosed;
import com.lord.arbam.model.WorkingDay;

@Component
public class RestoTableServiceCustomMapper {

	
	public RestoTableClosed  buildTableClosed(RestoTable findedTable,Employee employee,WorkingDay workingDay) {
		return  RestoTableClosed.builder().tableNumber(findedTable.getTableNumber())
				.employeeName(employee.getEmployeeName()).totalPrice(findedTable.getTotalTablePrice())
				.workingDay(workingDay).build();
	}
	
	public RestoTable setRestoTableToDefault(RestoTable findedTable) {
		
		findedTable.setEmployee(null);
		findedTable.setTableNumber(null);
		findedTable.setOpen(false);
		findedTable.setTotalTablePrice(null);
		return findedTable;
				
	}
	
	public List<RestoTableOrderClosedDto> mapOrderClosedsToDtos(List<RestoTableOrderClosed> orderCloseds){
		return orderCloseds.stream().map(orderClosed ->{
			RestoTableOrderClosedDto restoTableOrderClosedDto = new RestoTableOrderClosedDto();
		restoTableOrderClosedDto.setId(orderClosed.getId());
		restoTableOrderClosedDto.setProductName(orderClosed.getProductName());
		restoTableOrderClosedDto.setProductQuantity(orderClosed.getProductQuantity());
		restoTableOrderClosedDto.setTotalOrderPrice(orderClosed.getTotalOrderPrice());
	
		return restoTableOrderClosedDto;
		}).toList();
	}
	
	
	
	
	
}
