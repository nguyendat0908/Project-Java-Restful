package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User handleSaveUser(User user){
        return this.userRepository.save(user);
    }

    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }

    public User getUserById(long id){
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    public User updateUser(User user){

        User userOptional = this.getUserById(user.getId());
        
            userOptional.setEmail(user.getEmail());
            userOptional.setName(user.getName());
            userOptional.setPassword(user.getPassword());

            return this.userRepository.save(userOptional);
    }

    public User handleGetUserByUsername(String ussername){
        return this.userRepository.findByEmail(ussername);
    }
}
