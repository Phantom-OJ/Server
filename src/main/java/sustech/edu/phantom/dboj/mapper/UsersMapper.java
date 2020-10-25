package sustech.edu.phantom.dboj.mapper;
import sustech.edu.phantom.dboj.entity.*;
import java.util.*;

import org.apache.ibatis.annotations.Param;

public interface UsersMapper {

    Users login(String username, String password);

    int registerUser(Users users);

    Users findUserByusername(String username);
}
