package myappuserservice.springmyapp.service;

import myappuserservice.springmyapp.model.User;
import myappuserservice.springmyapp.repository.UserDTO;
import myappuserservice.springmyapp.repository.UserRepository;
import myappuserservice.springmyapp.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Service
//public class UserService {
//
//    private Validation validation = new Validation();
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User findUserById(Long id) {
//        if(validation.checkId(id.toString()))
//            return userRepository.getOne(id);
//        return null;
//    }
//
//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User saveUser(User user) {
//        if(validation.checkEmail(user.getEmail()) && validation.checkName(user.getName()) && validation.checkAge(user.getAge()))
//            return userRepository.save(user);
//        return null;
//    }
//
//    public void deleteById(Long id) {
//        if(validation.checkId(id.toString()))
//            userRepository.deleteById(id);
//    }
//}

@Service
public class UserService {

    private Validation validation = new Validation();

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO findUserById(Long id) {
        if (validation.checkId(id.toString())) {
            User user = userRepository.getOne(id);
            return convertToDTO(user);
        }
        return null;
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        if (validation.checkEmail(userDTO.getEmail()) &&
                validation.checkName(userDTO.getName()) &&
                validation.checkAge(userDTO.getAge())) {

            User user = convertToEntity(userDTO);
            User savedUser = userRepository.save(user);
            return convertToDTO(savedUser);
        }
        return null;
    }

    public void deleteById(Long id) {
        if (validation.checkId(id.toString())) {
            userRepository.deleteById(id);
        }
    }

    // Преобразование между User и UserDTO
    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getUser_id(), user.getName(), user.getEmail(), user.getAge());
    }

    private User convertToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUser_id(userDTO.getUser_id());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        user.setCreated_at(userDTO.getCreated_at());
        return user;
    }
}
