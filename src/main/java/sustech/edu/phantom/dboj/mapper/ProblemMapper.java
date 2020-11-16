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
}
