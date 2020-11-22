package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.AssignmentMapper;
import sustech.edu.phantom.dboj.mapper.GroupMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    GroupMapper groupMapper;

    public Assignment getOneAssignment(int id) {
        Assignment a = assignmentMapper.getOneAssignment(id);
        List<Problem> problemList = problemMapper.oneAssignmentProblems(id);
        a.setProblemList(problemList);
        a.setGroupList(groupMapper.getAssignmentGroup(id));
        return a;
    }

    public List<Assignment> getAssignmentList(Pagination pagination) {
        pagination.setParameters();
        List<Assignment> assignmentList = assignmentMapper.queryAssignments(pagination);
        for (Assignment a : assignmentList) {
            a.setGroupList(groupMapper.getAssignmentGroup(a.getId()));
        }
        return assignmentList;
    }
}
