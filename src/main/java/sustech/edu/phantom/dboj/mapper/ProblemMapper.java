package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface ProblemMapper {
    /**
     *
     * @param pagination
     * @return
     */
    List<Problem> queryProblem(Pagination pagination);

    /**
     *
     * @param id
     * @return
     */
    Problem queryCurrentProblem(int id);

    /**
     *
     * @param id
     * @return
     */
    int invalidateProblem(int id);

    /**
     *
     * @param id
     * @return
     */
    int validateProblem(int id);

    /**
     *
     * @param id
     * @return
     */
    List<Problem> oneAssignmentProblems(int id);

    /**
     *
     * @param id
     * @return
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
 * 还没写好
 */
//    List<Problem> multipleProblems(List<Integer> tagList);
}
