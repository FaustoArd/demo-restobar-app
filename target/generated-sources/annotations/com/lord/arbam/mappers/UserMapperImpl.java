package com.lord.arbam.mappers;

import com.lord.arbam.dtos.LoginResponseDto;
import com.lord.arbam.dtos.RegistrationDto;
import com.lord.arbam.models.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-05T20:55:05-0400",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.34.0.v20230523-1233, environment: Java 17.0.7 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User RegDtotoUser(RegistrationDto regDto) {
        if ( regDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( regDto.getEmail() );
        user.lastname( regDto.getLastname() );
        user.name( regDto.getName() );
        user.password( regDto.getPassword() );
        user.username( regDto.getUsername() );

        return user.build();
    }

    @Override
    public RegistrationDto userToRegDto(User user) {
        if ( user == null ) {
            return null;
        }

        RegistrationDto registrationDto = new RegistrationDto();

        registrationDto.setEmail( user.getEmail() );
        registrationDto.setLastname( user.getLastname() );
        registrationDto.setName( user.getName() );
        registrationDto.setPassword( user.getPassword() );
        registrationDto.setUsername( user.getUsername() );

        return registrationDto;
    }

    @Override
    public User LoginDtoToUser(LoginResponseDto LoginDto) {
        if ( LoginDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.password( LoginDto.getPassword() );
        user.username( LoginDto.getUsername() );

        return user.build();
    }
}
