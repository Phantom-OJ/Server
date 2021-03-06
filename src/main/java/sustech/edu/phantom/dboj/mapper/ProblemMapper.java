package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.form.upload.UploadProblemForm;

import java.util.List;

public interface ProblemMapper {
    /**
     * 无过滤器找problem
     *
     * @param pagination 分页过滤信息
     * @param isAdmin    是否是管理员
     * @return problem list
     */
    List<Problem> queryProblemWithoutFilter(Pagination pagination, @Param("flag") boolean isAdmin);

    /**
     * @param pagination
     * @return
     */
    Integer queryProblemWithoutFilterCounter(Pagination pagination, @Param("flag") boolean isAdmin);


    Problem queryCurrentProblem(@Param("id") int id, @Param("flag") boolean isAdmin);

    /**
     * @param id
     * @return
     */
    int invalidateProblem(int id);

    /**
     * <p>删除一个作业中的所有题目</p>
     * @param aid 作业id
     * @return 影响行数
     */
    int invalidateOneAssignmentProblems(int aid);

    /**
     * @param id
     * @return
     */
    int validateProblem(int id);

    /**
     * @param id
     * @return
     */
    List<Problem> oneAssignmentProblems(@Param("id") int id,@Param("isAdmin") boolean isAdmin);

    /**
     * @param id tag id
     * @return list of problems
     */
    List<Problem> oneTagProblems(int id);

    /**
     * 对提交的问题进行更新，提交次数和通过次数
     *
     * @param problem 待更新的实体类
     * @return 影响的行数
     */
    int updateProblemInfo(Problem problem);

    /**
     * 通过tags来找problem
     *
     * @param pagination 分页信息
     * @param tags       tags信息
     * @return list of problems
     */
    List<Problem> queryProblemsByTags(Pagination pagination, @Param("tags") List<Integer> tags);

    Integer queryProblemsByTagsCounter(Pagination pagination, @Param("tags") List<Integer> tags);

    /**
     * 通过tags 和 title 来查找信息
     *
     * @param pagination 分页信息
     * @param tags       tags
     * @param title      problem title
     * @return list of problems
     */
    List<Problem> queryProblemsByTagAndName(Pagination pagination, @Param("tags") List<Integer> tags, String title, @Param("flag2") boolean flag2, @Param("flag") boolean isAdmin);


    Integer queryProblemsByTagAndNameCounter(@Param("tags") List<Integer> tags, String title, @Param("flag2") boolean flag2, @Param("flag") boolean isAdmin);


    /**
     * 通过title 获取筛选问题
     *
     * @param pagination 分页信息
     * @param title      problem title
     * @return list of problems
     */
    List<Problem> queryProblemsByName(Pagination pagination, String title, @Param("flag") boolean isAdmin);

    Integer queryProblemsByNameCounter(Pagination pagination, String title, @Param("flag") boolean isAdmin);

    int saveProblem(Problem p);

    /**
     * 更新problem的状态公开可见
     *
     * @param list list of assignment id
     * @return 更新的行数
     */
    int publishProblems(@Param("list") List<Integer> list);

    /**
     * 更新problem的状态关闭
     *
     * @param list list of assignment id
     * @return 更新的行数
     */
    int closedProblems(@Param("list") List<Integer> list);

    List<Integer> problemGroups(int pid);

    int modifyProblem(@Param("pid") int pid, @Param("param2") UploadProblemForm param2);

    List<UploadProblemForm> getProblem(int aid);

    Long queryTime(int pid);
}
