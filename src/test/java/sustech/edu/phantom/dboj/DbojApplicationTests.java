package sustech.edu.phantom.dboj;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.entity.Users;
import sustech.edu.phantom.dboj.service.UsersService;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
class DbojApplicationTests {

    @Autowired
    UsersService usersService;

    @Test
    void testRegister(){
        Gson gson = new Gson();
        String obj = "{\"username\":\"11812318@mail.sustech.edu.cn\",\"password\":\"jfjefjf\",\"nickname\":\"lc\"}";
        Users users = gson.fromJson(obj,Users.class);
        Users a = usersService.registerAccount(users);
        System.out.println(gson.toJson(a));
    }
    @Test
    void testGson(){
        Users users = new Users();
        users.setUsername("11811408@mail.sustech.edu.cn");
        users.setPassword("jfjefjf");
        users.setNickname("lc");
        Gson gson = new Gson();
        String userJson = gson.toJson(users);
        System.out.println(userJson);
    }

}
