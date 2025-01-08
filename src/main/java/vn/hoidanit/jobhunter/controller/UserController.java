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

import vn.hoidanit.jobhunter.domain.User;
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

    @GetMapping("/users")
    @ApiMessage("Get all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(@Filter Specification<User> spec, Pageable pageable) {

        return ResponseEntity.ok(this.userService.getAllUsers(spec, pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        User newUsesr = this.userService.handleSaveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUsesr);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {

        if (id >= 1500) {
            throw new IdInvalidException("Id khong lon hon 1500");
        }

        this.userService.deleteUserById(id);

        return ResponseEntity.ok("Success!");
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.updateUser(user));
    }
}
