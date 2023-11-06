package com.lord.arbam.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dtos.LoginResponseDto;
import com.lord.arbam.dtos.RegistrationDto;
import com.lord.arbam.models.User;

@Mapper
public interface UserMapper {

	public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mapping(target="id", ignore = true)
	@Mapping(target = "enabled", ignore=true)
	@Mapping(target="authorities", ignore = true)
	public User RegDtotoUser(RegistrationDto regDto);
	
	
	public RegistrationDto userToRegDto(User user);
	
	@Mapping(target="id", ignore = true)
	@Mapping(target = "name", ignore = true)
	@Mapping(target="lastname", ignore = true)
	@Mapping(target="email", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	@Mapping(target="authorities", ignore = true)
	public User LoginDtoToUser(LoginResponseDto LoginDto);
	
	
	
}
