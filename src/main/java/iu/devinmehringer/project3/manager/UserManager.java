package iu.devinmehringer.project3.manager;

import iu.devinmehringer.project3.access.UserAccess;
import iu.devinmehringer.project3.controller.exception.UnauthorizedException;
import iu.devinmehringer.project3.model.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserManager {

    private final UserAccess userAccess;

    public UserManager(UserAccess userAccess) {
        this.userAccess = userAccess;
    }

    public User resolveUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new UnauthorizedException("Not logged in");
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals("userId"))
                .findFirst()
                .map(c -> userAccess.getById(Long.parseLong(c.getValue()))
                        .orElseThrow(() -> new UnauthorizedException("Invalid session")))
                .orElseThrow(() -> new UnauthorizedException("Not logged in"));
    }

    public List<User> getAllUsers() {
        return userAccess.getAllUsers();
    }
}