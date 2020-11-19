package sustech.edu.phantom.dboj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api")
public class BeaconController {
    @RequestMapping(value="/beacon",method = RequestMethod.POST)
    public String beacon(HttpServletRequest request) throws Exception{
        var body = new String(request.getInputStream().readAllBytes());
        System.out.println(body);
        return body;
    }
}
