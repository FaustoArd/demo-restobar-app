package com.lord.arbam.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.WorkingDayDto;
import com.lord.arbam.model.WorkingDay;

@Mapper
public interface  WorkingDayMapper {

	public static WorkingDayMapper INSTANCE = Mappers.getMapper(WorkingDayMapper.class);
	

	public WorkingDay toWorkingDay(WorkingDayDto workingDayDto);

	
	public  WorkingDayDto toWorkingDayDto(WorkingDay workingDay);

	
	public  List<WorkingDayDto> toWorkingDaysDto(List<WorkingDay> workingDays);
}
