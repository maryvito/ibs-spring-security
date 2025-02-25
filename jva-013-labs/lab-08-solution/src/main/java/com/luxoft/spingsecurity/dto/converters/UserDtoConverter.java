package com.luxoft.spingsecurity.dto.converters;

import com.luxoft.spingsecurity.dto.UserDto;
import com.luxoft.spingsecurity.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public UserDto toDto(User domain) {
        return new UserDto(domain.getId(), domain.getLogin(), null, domain.getRoles());
    }

    public User toDomain(UserDto dto) {
        var user = new User();
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());
        return user;
    }
}
