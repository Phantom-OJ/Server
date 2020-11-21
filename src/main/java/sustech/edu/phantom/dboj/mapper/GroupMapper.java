package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.Group;

import java.util.ArrayList;
import java.util.List;

public interface GroupMapper {
    List<Group> getAllGroups();


    List<Group> getGroups(@Param("ids") ArrayList<Integer> ids);

    List<Group> getAssignmentGroup(int aid);

    List<Group> getStudentGroup(int sid);
}
