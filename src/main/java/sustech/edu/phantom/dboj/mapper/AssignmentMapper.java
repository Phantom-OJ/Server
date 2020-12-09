package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Assignment;
import sustech.edu.phantom.dboj.form.home.Pagination;

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
    List<Assignment> queryAssignmentsWithoutFilter(Pagination pagination,boolean isAdmin);

    Integer queryAssignmentsWithoutFilterCounter(Pagination pagination, boolean isAdmin);

    /**
     * 返回分页的作业信息，过滤器为assignment title
     *
     * @param pagination 分页信息
     * @param name       assignment title
     * @return list of assignment
     */
    List<Assignment> queryAssignmentByName(Pagination pagination, String name, boolean isAdmin);

    Integer queryAssignmentByNameCounter(Pagination pagination, String name, boolean isAdmin);

    /**
     * 返回所有的作业
     *
     * @return 作业 list
     */
    List<Assignment> getAllAssignment(boolean isAdmin);

    /**
     * 返回一个assignment的信息
     *
     * @param aid assignment id
     * @return assignment对象
     */
    Assignment getOneAssignment(int aid, boolean isAdmin);


    /**
     * 使一个assignment失效
     *
     * @param aid assignment id
     * @return 影响的行数
     */
    int invalidateAssignment(int aid);

    int saveAssignment(Assignment a);

    int publishAssignment(@Param("list") List<Integer> list);

    int closeAssignment(@Param("list") List<Integer> list);

    /**
     * 找到要更新成public的题目
     *
     * @return assignment id list
     */
    List<Integer> get2BPublicId();

    /**
     * 找到要更新成closed的题目
     *
     * @return assignment id list
     */
    List<Integer> get2BClosedId();
}
