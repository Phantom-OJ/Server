package sustech.edu.phantom.dboj.mapper;
import sustech.edu.phantom.dboj.entity.User;

public interface UserMapper {
    User loginUser(User user);

    int registerUser(User user);

    int findByUsername(String username);
}
