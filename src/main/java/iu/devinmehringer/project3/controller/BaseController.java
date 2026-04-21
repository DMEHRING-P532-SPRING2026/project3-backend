package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.controller.dto.BaseRequest;
import iu.devinmehringer.project3.manager.UserManager;
import iu.devinmehringer.project3.model.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {

    private final UserManager userManager;

    public BaseController(UserManager userManager) {
        this.userManager = userManager;
    }

    protected User resolveUser(HttpServletRequest httpRequest) {
        return userManager.resolveUser(httpRequest);
    }

    protected void stampUser(BaseRequest request, HttpServletRequest httpRequest) {
        request.setPerformedBy(resolveUser(httpRequest));
    }
}