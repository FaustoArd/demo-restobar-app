package com.lord.arbam.mapper;

import com.lord.arbam.dto.LoginResponseDto;
import com.lord.arbam.dto.RegistrationDto;
import com.lord.arbam.dto.UserDto;
import com.lord.arbam.model.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T17:19:11-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User RegDtotoUser(RegistrationDto regDto) {
        if ( regDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( regDto.getName() );
        user.lastname( regDto.getLastname() );
        user.username( regDto.getUsername() );
        user.email( regDto.getEmail() );
        user.password( regDto.getPassword() );

        return user.build();
    }

    @Override
    public RegistrationDto userToRegDto(User user) {
        if ( user == null ) {
            return null;
        }

        RegistrationDto registrationDto = new RegistrationDto();

        registrationDto.setName( user.getName() );
        registrationDto.setLastname( user.getLastname() );
        registrationDto.setUsername( user.getUsername() );
        registrationDto.setEmail( user.getEmail() );
        registrationDto.setPassword( user.getPassword() );

        return registrationDto;
    }

    @Override
    public User LoginDtoToUser(LoginResponseDto LoginDto) {
        if ( LoginDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( LoginDto.getUsername() );
        user.password( LoginDto.getPassword() );

        return user.build();
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setName( user.getName() );
        userDto.setLastname( user.getLastname() );
        userDto.setUsername( user.getUsername() );
        userDto.setEmail( user.getEmail() );

        return userDto;
    }
}
