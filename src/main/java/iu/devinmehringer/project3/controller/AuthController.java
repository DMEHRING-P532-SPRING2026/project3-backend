package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.access.UserAccess;
import iu.devinmehringer.project3.controller.exception.UserNotFoundException;
import iu.devinmehringer.project3.model.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccess userAccess;

    public AuthController(UserAccess userAccess) {
        this.userAccess = userAccess;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userAccess.getAllUsers());
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestParam String username, HttpServletResponse response) {
        User user = userAccess.getByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}