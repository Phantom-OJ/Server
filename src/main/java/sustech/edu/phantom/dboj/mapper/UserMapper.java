package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.User;

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
}
