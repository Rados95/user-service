package rs.radosacimovic.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.radosacimovic.userservice.model.UserEntity;
import rs.radosacimovic.userservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public List<UserEntity> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }

}
