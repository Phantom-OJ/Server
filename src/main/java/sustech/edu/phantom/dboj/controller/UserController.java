package sustech.edu.phantom.dboj.controller;

import com.google.gson.Gson;

import sustech.edu.phantom.dboj.entity.*;
import sustech.edu.phantom.dboj.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    UsersService usersService;

    @ResponseBody
    @RequestMapping(value = "/api/user/signup", method = RequestMethod.POST)
    public Users signup(@RequestBody Users user) {
        System.out.println(user);
        return user;
    }


}
