package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

/**
 * @author Lori
 */
public interface AssignmentMapper {
    /**
     * 返回分页的作业信息
     *
     * @param pagination 分页信息
     * @return 作业 list
     */
    List<Assignment> queryAssignmentsWithoutFilter(Pagination pagination);

    /**
     * 返回分页的作业信息，过滤器为assignment title
     * @param pagination 分页信息
     * @param name assignment title
     * @return list of assignment
     */
    List<Assignment> queryAssignmentByName(Pagination pagination, String name);

    /**
     * 返回所有的作业
     *
     * @return 作业 list
     */
    List<Assignment> getAllAssignment();

    /**
     * 返回一个assignment的信息
     *
     * @param aid assignment id
     * @return assignment对象
     */
    Assignment getOneAssignment(int aid);

    /**
     * 使一个assignment失效
     *
     * @param aid assignment id
     * @return 影响的行数
     */
    int invalidateAssignment(int aid);
//剩下来的修改作业的sql我都没加，暂时先搁置
}
