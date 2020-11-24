package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface ProblemMapper {
    /**
     * @param pagination
     * @return
     */
    List<Problem> queryProblemWithoutFilter(Pagination pagination);

    Integer queryProblemWithoutFilterCounter(Pagination pagination);

    /**
     * @param id
     * @return
     */
    Problem queryCurrentProblem(int id);

    /**
     * @param id
     * @return
     */
    int invalidateProblem(int id);

    /**
     * @param id
     * @return
     */
    int validateProblem(int id);

    /**
     * @param id
     * @return
     */
    List<Problem> oneAssignmentProblems(int id);

    /**
     * @param pagination 分页过滤信息
     * @param id         tag id
     * @return list of problems
     */
    List<Problem> oneTagProblems(Pagination pagination, int id);

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
    List<Problem> queryProblemsByTagAndName(Pagination pagination, @Param("tags") List<Integer> tags, String title);


    Integer queryProblemsByTagAndNameCounter(Pagination pagination, @Param("tags") List<Integer> tags, String title);


    /**
     * 通过title 获取筛选问题
     *
     * @param pagination 分页信息
     * @param title      problem title
     * @return list of problems
     */
    List<Problem> queryProblemsByName(Pagination pagination, String title);

    Integer queryProblemsByNameCounter(Pagination pagination, String title);
}
