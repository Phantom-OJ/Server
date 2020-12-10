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
     * 返回作业信息
     *
     * @param pagination 分页信息
     * @return 作业 list
     */
    List<Assignment> queryAssignmentsWithoutFilter(Pagination pagination, @Param("flag") boolean isAdmin);

    /**
     * 跟上面一样<br></br>
     * 返回上面作业信息的所有数量
     *
     * @param isAdmin 是否有更高权限
     * @return 数量
     */
    Integer queryAssignmentsWithoutFilterCounter(@Param("flag") boolean isAdmin);

    /**
     * 返回分页的作业信息，过滤器为assignment title
     *
     * @param pagination 分页信息
     * @param name       assignment title
     * @return 作业信息
     */
    List<Assignment> queryAssignmentByName(Pagination pagination, String name,@Param("flag2") boolean flag2, @Param("flag") boolean isAdmin);

    /**
     * 返回上面方法得到作业信息的总数
     *
     * @param name    assignment title
     * @param isAdmin 是否有更高权限
     * @return 数量
     */
    Integer queryAssignmentByNameCounter(String name,@Param("flag2") boolean flag2, @Param("flag") boolean isAdmin);

    /**
     * 返回所有的作业
     *
     * @return 所有的作业
     */
    List<Assignment> getAllAssignment(@Param("flag") boolean isAdmin);

    /**
     * 返回一个assignment的信息
     *
     * @param aid assignment id
     * @return 对应的assignment
     */
    Assignment getOneAssignment(int aid, @Param("flag") boolean isAdmin);


    /**
     * 使一个assignment失效
     *
     * @param aid assignment id
     * @return 影响的行数
     */
    int invalidateAssignment(int aid);

    /**
     * 插入一条assignment
     * @param a assignment
     * @return 插入成功返回1
     */
    int saveAssignment(Assignment a);

    /**
     * 公开一个作业
     * @param list
     * @return
     */
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
