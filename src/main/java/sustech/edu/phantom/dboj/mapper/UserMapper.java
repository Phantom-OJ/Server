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
     * update information
     * @param userForm user form
     * @param userId user id
     * @return boolean
     */
    boolean updateUserInfo(UserForm userForm, int userId);

    /**
     * 通过id找user
     * @param userId user id
     * @return User 对象
     */
    User findUserById(int userId);

    boolean resetPassword(String username, String password);

    int modifyPassword(String username, String password);
}
