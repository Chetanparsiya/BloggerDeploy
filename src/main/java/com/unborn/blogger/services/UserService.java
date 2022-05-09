package com.unborn.blogger.services;

import com.unborn.blogger.datatransferobject.UserDto;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto userDto);

    public UserDto getUser(Long id);

    public List<UserDto> getUsers();

    public UserDto updateUser(UserDto userDto, Long id);

    public void deleteUser(Long id);


}
