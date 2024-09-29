package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.UserRequest;
import az.company.onlinelibrarysystem.dto.response.UserResponse;
import az.company.onlinelibrarysystem.enums.Role;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.enums.EnumAvailableStatus;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserResponse addUser(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        checkEmailAndUsername(userRequest);

        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);
        return userResponse;
    }

    private void checkEmailAndUsername(UserRequest userRequest) {
        if (userRepository
                .findByUsernameAndStatus(userRequest.getUsername(), EnumAvailableStatus.ACTIVE.getCode()).isPresent()) {
            throw new CustomException("Username already exists");
        }

        if (userRepository
                .findByEmailAndStatus(userRequest.getEmail(), EnumAvailableStatus.ACTIVE.getCode()).isPresent()) {
            throw new CustomException("Email already exists");
        }
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Long id) {
        User user = getUser(userRepository.findByIdAndStatus(id, EnumAvailableStatus.ACTIVE.getCode()));
        checkEmailAndUsername(userRequest);
        BeanUtils.copyProperties(userRequest, user);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User updatedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(updatedUser, userResponse);
        return userResponse;
    }

    private User getUser(Optional<User> userRepository) {
        return userRepository
                .orElseThrow(() -> new CustomException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = getUser(userRepository.findById(userId));
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAllByStatus(EnumAvailableStatus.ACTIVE.getCode());
        return users.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    BeanUtils.copyProperties(user, userResponse);
                    return userResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsernameAndStatus(username, EnumAvailableStatus.ACTIVE.getCode())
                .orElseThrow(() -> new CustomException("User not found with username: " + username));
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    @Override
    public List<UserResponse> getUsersByRole(Role role) {
        List<User> users = userRepository.findByRoleAndStatus(role, EnumAvailableStatus.ACTIVE.getCode());
        return users.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    BeanUtils.copyProperties(user, userResponse);
                    return userResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = getUser(userRepository.findById(userId));
        user.setStatus(0);
        // Perform deactivation logic
        userRepository.save(user);
    }


}
