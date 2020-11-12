package sustech.edu.phantom.dboj.mapper;

import java.util.List;

public interface PermissionMapper {
    List<String> getUserPermission(char role);
}
