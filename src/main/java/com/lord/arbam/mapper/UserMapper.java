package com.lord.arbam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lord.arbam.dto.LoginResponseDto;
import com.lord.arbam.dto.RegistrationDto;
import com.lord.arbam.dto.UserDto;
import com.lord.arbam.model.User;

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
	
	
	public UserDto toUserDto(User user);
	
	
	
}
