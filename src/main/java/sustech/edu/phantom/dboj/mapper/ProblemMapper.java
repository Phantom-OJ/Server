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
}
