package sustech.edu.phantom.dboj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.service.UserService;

import java.util.List;

@RestController

public class AdministratorController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/announcementlist", method = RequestMethod.POST)
    public int insertAnnouncementList(@RequestBody List<Announcement> announcementList) {
        return userService.insertAnnouncementList(announcementList);
    }
}
