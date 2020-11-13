package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Permission;

import java.util.List;

public interface PermissionMapper {
    List<Permission> getUserPermission(String role);
}
