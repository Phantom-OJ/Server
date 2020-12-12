package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sustech.edu.phantom.dboj.entity.po.Permission;
import sustech.edu.phantom.dboj.mapper.GroupMapper;
import sustech.edu.phantom.dboj.mapper.PermissionMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 01:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AdvancedInfoModificationService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    GroupMapper groupMapper;


    public void grantUser(Map<String, List<Integer>> hm) {
        var roleList = permissionMapper.getPermissions().stream().map(Permission::getRole).distinct()
                .collect(Collectors.toList());
        roleList.add("ROLE_STUDENT");
        try {
            for (Map.Entry<String, List<Integer>> entry : hm.entrySet()) {
                if (roleList.contains(entry.getKey())) {
                    userMapper.grantUser(entry.getKey(), entry.getValue());
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    break;
                }
            }
        } catch (Exception e) {
            log.error("something happens in the internal server.");
        }
    }

    public int modifyPermission(Permission permission, boolean delete, int id) {
        return delete ? permissionMapper.invalidatePermission(id) : permissionMapper.savePermission(permission);
    }

    public int modifyGroup(String description, boolean delete, int id) {
        if (delete) {
            groupMapper.invalidateGroupAssignment(id);
            groupMapper.invalidateGroupUser(id);
            return groupMapper.invalidateGroup(id);
        } else {
            return groupMapper.saveGroup(description);
        }
    }
}
