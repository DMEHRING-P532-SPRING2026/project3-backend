package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.UserRepository;
import iu.devinmehringer.project3.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccess {
    private final UserRepository userRepository;

    public UserAccess(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(userRepository.getByUsername(username));
    }

    public void save(User user) {
        userRepository.save(user);
    }
    
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }
}
