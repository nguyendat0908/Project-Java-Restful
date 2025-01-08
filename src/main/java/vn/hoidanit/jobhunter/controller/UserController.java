package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.DTO.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.ResUserDTO;
import vn.hoidanit.jobhunter.domain.DTO.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users
    @GetMapping("/users")
    @ApiMessage("Get all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(@Filter Specification<User> spec, Pageable pageable) {

        return ResponseEntity.ok(this.userService.getAllUsers(spec, pageable));
    }

    // Get user by id
    @GetMapping("/users/{id}")
    @ApiMessage("Get a user by ID")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws IdInvalidException {

        User user = this.userService.getUserById(id);
        if (user == null) {
            throw new IdInvalidException("User " + id + " không tồn tại!");
        }

        return ResponseEntity.ok(this.userService.convertUserToResUserDTO(user));
    }

    // Create new user
    @PostMapping("/users")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User user) throws IdInvalidException {

        boolean isExist = this.userService.checkExistEmail(user.getEmail());

        if (isExist) {
            throw new IdInvalidException("Email " + user.getEmail() + " đã tồn tại. Vui lòng sử dụng email khác!");
        }

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        User newUser = this.userService.handleSaveUser(user);

        return ResponseEntity.ok(this.userService.convertUserToResCreateUserDTO(newUser));
    }

    // Delete user by id
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws IdInvalidException {

        User user = this.userService.getUserById(id);
        if (user == null) {
            throw new IdInvalidException("User " + id + " không tồn tại!");
        }

        this.userService.deleteUserById(id);

        return ResponseEntity.ok(null);
    }

    // Update user
    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {

        User userOptional = this.userService.updateUser(user);

        if (userOptional == null) {
            throw new IdInvalidException("User " + user.getId() + " không tồn tại!");
        }

        return ResponseEntity.ok(this.userService.convertUserToResUpdateUserDTO(userOptional));
    }
}
