package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lori
 */
public interface GroupMapper {
    /**
     *
     * @return
     */
    List<Group> getAllGroups();

    /**
     *
     * @param ids
     * @return
     */
    List<Group> getGroups(@Param("ids") ArrayList<Integer> ids);

    /**
     *
     * @param aid
     * @return
     */
    List<Group> getAssignmentGroup(int aid);

    /**
     *
     * @param sid
     * @return
     */
    List<Group> getStudentGroup(int sid);

    int saveGroup(String description);

    int invalidateGroup(int gid);

    int invalidateGroupUser(int gid);

    int invalidateGroupAssignment(int gid);
}
