package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.DTO.Meta;
import vn.hoidanit.jobhunter.domain.DTO.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.ResUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create new user
    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    // Delete user by id
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    // Get user by id
    public User getUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    // Get all users
    public ResultPaginationDTO getAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageUser.getNumber() + 1);
        meta.setPageSize(pageUser.getSize());
        meta.setPages(pageUser.getTotalPages());
        meta.setTotal(pageUser.getTotalElements());

        resultPaginationDTO.setMeta(meta);

        List<ResUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> new ResUserDTO(
                        item.getId(),
                        item.getAge(),
                        item.getName(),
                        item.getEmail(),
                        item.getAddress(),
                        item.getGender(),
                        item.getCreatedAt(),
                        item.getUpdatedAt()))
                .collect(Collectors.toList());

        resultPaginationDTO.setResult(listUser);

        return resultPaginationDTO;
    }

    // Update user
    public User updateUser(User user) {

        User userOptional = this.getUserById(user.getId());

        if (userOptional != null) {
            userOptional.setAge(user.getAge());
            userOptional.setAddress(user.getAddress());
            userOptional.setName(user.getName());
            userOptional.setGender(user.getGender());

            this.userRepository.save(userOptional);
        }

        return userOptional;
    }

    // Get user by username
    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    // Check exist email
    public boolean checkExistEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    // Convert user to response DTO
    public ResUserDTO convertUserToResUserDTO(User user) {
        ResUserDTO resUserDTO = new ResUserDTO();

        resUserDTO.setId(user.getId());
        resUserDTO.setAge(user.getAge());
        resUserDTO.setName(user.getName());
        resUserDTO.setEmail(user.getEmail());
        resUserDTO.setAddress(user.getAddress());
        resUserDTO.setGender(user.getGender());
        resUserDTO.setCreatedBy(user.getCreatedAt());
        resUserDTO.setUpdatedBy(user.getUpdatedAt());

        return resUserDTO;
    }

    // Convert user to response create DTO
    public ResCreateUserDTO convertUserToResCreateUserDTO(User user) {
        ResCreateUserDTO resCreateUserDTO = new ResCreateUserDTO();

        resCreateUserDTO.setId(user.getId());
        resCreateUserDTO.setAge(user.getAge());
        resCreateUserDTO.setName(user.getName());
        resCreateUserDTO.setEmail(user.getEmail());
        resCreateUserDTO.setAddress(user.getAddress());
        resCreateUserDTO.setGender(user.getGender());
        resCreateUserDTO.setCreatedBy(user.getCreatedAt());

        return resCreateUserDTO;
    }

    // Convert user to response update DTO
    public ResUpdateUserDTO convertUserToResUpdateUserDTO(User user) {
        ResUpdateUserDTO resUpdateUserDTO = new ResUpdateUserDTO();

        resUpdateUserDTO.setId(user.getId());
        resUpdateUserDTO.setAge(user.getAge());
        resUpdateUserDTO.setName(user.getName());
        resUpdateUserDTO.setAddress(user.getAddress());
        resUpdateUserDTO.setGender(user.getGender());
        resUpdateUserDTO.setUpdateAt(user.getUpdatedAt());

        return resUpdateUserDTO;
    }

    // Update user token
    public void updateUserToken(String token, String email){
        User user = this.handleGetUserByUsername(email);
        if (user != null) {
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }
}
