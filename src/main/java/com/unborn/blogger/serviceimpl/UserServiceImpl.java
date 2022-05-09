package com.unborn.blogger.serviceimpl;

import com.unborn.blogger.entities.User;
import com.unborn.blogger.exceptions.ResourceNotFoundException;
import com.unborn.blogger.datatransferobject.UserDto;
import com.unborn.blogger.repositories.UserRepo;
import com.unborn.blogger.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        UserDto createdUser = modelMapper.map(userRepo.save(user), UserDto.class);
        return createdUser;
    }

    public UserDto getUser(Long id){
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return userToUserDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id", id));
        user.setEmail(userDto.getEmail());
        user.setFirstName((userDto.getFirstName()));
        user.setLastName(userDto.getLastName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user = userRepo.save(user);
        return userToUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private UserDto userToUserDto(User user){
       return modelMapper.map(user, UserDto.class);
    }

    private User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

}
