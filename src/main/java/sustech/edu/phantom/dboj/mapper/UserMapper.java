package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.form.modification.UserForm;

import java.util.List;

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
    int updateUserInfo(UserForm userForm, int userId);

    /**
     * 通过id找user
     * @param userId user id
     * @return User 对象
     */
    User findUserById(int userId);

    boolean resetPassword(String username, String password);

    int modifyPassword(String username, String password);

    void saveState(String state, int id);

    String getState(int id);

    List<User> findUserByGroup(int gid);

    List<User> findNotUserByGroup(int gid);

    int uploadAvatar(String filePath, int uid);

    int grantUser(@Param("list") String role, List<Integer> list);

    List<User> findUserByFilter(String filter);

    List<Group> findOneUserGroup(int uid);
}
