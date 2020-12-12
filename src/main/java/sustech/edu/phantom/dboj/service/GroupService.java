package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.mapper.GroupMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GroupService {
    @Autowired
    GroupMapper groupMapper;

    @Autowired
    UserMapper userMapper;

    public List<Group> getGroupList(ArrayList<Integer> a) {
        return groupMapper.getGroups(a);
    }

    public List<Group> getAllGroups() {
        return groupMapper.getAllGroups();
    }

    public boolean addOneUser2Group(Integer uid, Integer gid) {
        int a = userMapper.checkUsrGroup(uid, gid);
        if (a > 0) {
            return userMapper.saveUserGroup(uid, gid, true);
        } else {
            return userMapper.saveUserGroup(uid, gid, false);
        }
    }

    public boolean deleteUserFromGroup(Integer uid, Integer gid) {
        return userMapper.deleteUserGroup(uid, gid);
    }
}
