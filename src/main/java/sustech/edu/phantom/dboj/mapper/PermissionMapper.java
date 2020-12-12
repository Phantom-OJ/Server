package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.po.Permission;

import java.util.List;

/**
 * @author Lori
 */
public interface PermissionMapper {
    /**
     * 得到一个用户的权限
     * @param role 角色
     * @return 权限列表
     */
    List<String> getUserPermission(String role);

    /**
     * 得到所有的权限
     * @return 权限列表
     */
    List<Permission> getPermissions();

    /**
     * 插入权限
     * @param permission 权限对象
     * @return 影响的行数
     */
    int savePermission(Permission permission);

    /**
     * 删除权限
     * @param id 权限id
     * @return 影响的行数
     */
    int invalidatePermission(int id);

    /**
     * 得到是否有这个权限
     * @param role 角色
     * @param p allowance
     * @return 表中是否有这个权限
     */
    Integer getPermission(String role, String p);

    /**
     * <p>
     *     恢复权限
     * </p>
     * @param id 权限id
     * @return 是否成功
     */
    Integer validatePermission(int id);
}
