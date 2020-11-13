package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface AssignmentMapper {
    /**
     *
     * @param pagination
     * @return
     */
    List<Assignment> getAssignment(Pagination pagination);

    /**
     *
     * @return
     */
    List<Assignment> getAllAssignment();

    /**
     *
     * @param aid
     * @return
     */
    Assignment getOneAssignment(int aid);

}
