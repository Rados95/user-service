package rs.radosacimovic.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.radosacimovic.userservice.dto.LoginRequestDTO;
import rs.radosacimovic.userservice.dto.UserRegistrationDTO;
import rs.radosacimovic.userservice.dto.UserDTO;
import rs.radosacimovic.userservice.model.UserEntity;
import rs.radosacimovic.userservice.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/users")
    private ResponseEntity<UserDTO> save(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserEntity userEntity = userService.save(this.convertToEntity(userRegistrationDTO));

        return new ResponseEntity<>(this.convertToDTO(userEntity), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false) String role) {
        List<UserEntity> userEntities;
        if (role == null) {
            userEntities = userService.getUsers();
        } else {
            userEntities = userService.getUsersByRole(role);
        }
        return ResponseEntity.ok(userEntities.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<UserEntity> userEntity = userService.getUserByUsername(username);
        return userEntity.map(entity -> ResponseEntity.ok(this.convertToDTO(entity))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        boolean deletionSuccessful = userService.deleteUser(username);
        if (deletionSuccessful) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/login")
    private ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<UserEntity> userEntity = userService.getUserByUsername(loginRequestDTO.getUsername());
        boolean loginSuccessful = userEntity.isPresent() && userEntity.get().getPassword().equals(loginRequestDTO.getPassword());
        if (loginSuccessful) {
            return ResponseEntity.ok(convertToDTO(userEntity.get()));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private UserDTO convertToDTO(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }

    private UserEntity convertToEntity(UserRegistrationDTO userRegistrationDTO) {
        return modelMapper.map(userRegistrationDTO, UserEntity.class);
    }

}
