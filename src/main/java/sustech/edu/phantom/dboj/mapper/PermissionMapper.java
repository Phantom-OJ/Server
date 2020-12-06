package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.Permission;

import java.util.List;

/**
 * @author Lori
 */
public interface PermissionMapper {
    List<String> getUserPermission(String role);

    int save(@Param("list") List<Permission> list);
}
