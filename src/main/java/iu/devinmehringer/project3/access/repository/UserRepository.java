package iu.devinmehringer.project3.access.repository;

import iu.devinmehringer.project3.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);
}
