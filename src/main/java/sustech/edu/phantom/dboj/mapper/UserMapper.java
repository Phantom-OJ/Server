package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.UserForm;

public interface UserMapper {
    /**
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     *
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     *
     * @param uid
     * @param role
     * @return
     */
    int privilegeEscalation(int uid, char role);

    /**
     *
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     *
     * @param userForm
     * @return
     */
    User updateUserInfo(UserForm userForm, int userId);
}
