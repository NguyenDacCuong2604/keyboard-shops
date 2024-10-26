package com.example.keyboardshops.service.user;

import com.example.keyboardshops.dto.UserDto;
import com.example.keyboardshops.model.User;
import com.example.keyboardshops.request.CreateUserRequest;
import com.example.keyboardshops.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
