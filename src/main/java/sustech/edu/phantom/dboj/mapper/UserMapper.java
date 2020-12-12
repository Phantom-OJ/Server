package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.form.modification.UserForm;
import sustech.edu.phantom.dboj.form.search.GroupRoleForm;

import java.util.List;

public interface UserMapper {
    /**
     * <p>
     *     初始化用户群组
     * </p>
     * <message>
     *     初始化群组为 1 -> all_members
     * </message>
     * @param id 用户id
     * @return 是否插入成功
     */
    boolean initUserGroup(int id);

    /**
     * 注册用户
     * @param user 待注册对象
     * @return 是否成功
     */
    boolean register(User user);

    /**
     * 通过用户名找用户
     * @param username 用户名 严格查找
     * @return 用户对象
     */
    User findUserByUsername(String username);


    /**
     * 通过nickname或者username查找用户
     * @param name nickname或者username 严格查找
     * @return 用户对象
     */
    User findUserByName(String name);

    /**
     * 待更新的对象
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

    /**
     * 重置密码
     * @param username 用户名
     * @param password 新密码
     * @return 是否成功
     */
    boolean resetPassword(String username, String password);

    /**
     * 修改密码
     * @param username 用户名
     * @param password 新密码
     * @return 是否成功
     */
    int modifyPassword(String username, String password);

    /**
     * 保存状态
     * @param state 退出前的状态
     * @param id 用户id
     */
    void saveState(String state, int id);

    /**
     * 得到用户上次状态
     * @param id 用户id
     * @return 用户上次状态
     */
    String getState(int id);

    /**
     * <message>
     *     上传头像
     * </message>
     * @param filePath 图片路径
     * @param uid user id
     * @return 是否成功
     */
    int uploadAvatar(String filePath, int uid);

    /**
     * 修改用户角色信息
     * @param role 角色
     * @param list 待修改角色的用户id列表
     * @return 修改的行数
     */
    int grantUser(@Param("role") String role, List<Integer> list);

    /**
     * 找到一个用户属于的群组
     * @param uid 用户id
     * @return 所属的群组列表
     */
    List<Group> findOneUserGroup(int uid);

    /**
     * 模糊搜索用户
     * @param list 过滤器
     * @return 用户列表
     */
    List<User> findUserByFilterMixed(@Param("list") GroupRoleForm list);

    int checkUsrGroup(int uid, int gid);

    /**
     *
     * @param uid
     * @param gid
     * @param flag 是否存在该字段
     * @return
     */
    boolean saveUserGroup(int uid, int gid,boolean flag);

    /**
     * 删除用户所在的group
     * @param uid 用户id
     * @param gid 群组id
     * @return 是否成功
     */
    boolean deleteUserGroup(int uid, int gid);
}
