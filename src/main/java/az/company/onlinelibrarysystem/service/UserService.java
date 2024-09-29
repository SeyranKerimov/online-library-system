package az.company.onlinelibrarysystem.service;


import java.util.List;

import az.company.onlinelibrarysystem.dto.request.UserRequest;
import az.company.onlinelibrarysystem.dto.response.UserResponse;
import az.company.onlinelibrarysystem.enums.Role;

public interface UserService {

    UserResponse addUser(UserRequest user);

    UserResponse updateUser(UserRequest user,Long id);

    void deleteUser(Long userId);

    UserResponse getUserById(Long userId);

    List<UserResponse> getAllUsers();

    UserResponse getUserByUsername(String username);

    List<UserResponse> getUsersByRole(Role role);

    void deactivateUser(Long userId);

}
