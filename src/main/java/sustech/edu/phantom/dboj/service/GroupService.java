package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.mapper.GroupMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GroupService {
    @Autowired
    GroupMapper groupMapper;

    public List<Group> getGroupList(ArrayList<Integer> a) {
        return groupMapper.getGroups(a);
    }

    public List<Group> getAllGroups() {

        return groupMapper.getAllGroups();
    }
}
