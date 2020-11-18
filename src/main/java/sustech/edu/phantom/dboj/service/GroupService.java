package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Group;
import sustech.edu.phantom.dboj.mapper.GroupMapper;

import java.util.ArrayList;
import java.util.List;
@Service
public class GroupService {
    @Autowired
    GroupMapper groupMapper;

    public List<Group> getGroupList(ArrayList<Integer> a){
        return groupMapper.getGroups(a);
    }
}
