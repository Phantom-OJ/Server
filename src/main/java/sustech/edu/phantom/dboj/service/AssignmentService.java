package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.AssignmentMapper;
import sustech.edu.phantom.dboj.mapper.GroupMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AssignmentService {
    private final static String ID = "id";
    private final static String NAME = "name";
    //可能还会有一个group

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    GroupMapper groupMapper;

    /**
     * @param id
     * @return
     */
    public Assignment getOneAssignment(int id) {
        Assignment a = assignmentMapper.getOneAssignment(id);
        List<Problem> problemList = problemMapper.oneAssignmentProblems(id);
        a.setProblemList(problemList);
        a.setGroupList(groupMapper.getAssignmentGroup(id));
        return a;
    }

    /**
     * @param pagination 分页信息
     * @return list of assignments
     */
    public List<Assignment> getAssignmentList(Pagination pagination) {
        pagination.setParameters();

        List<Assignment> assignmentList = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String idString = (String) hm.get(ID);
        String name = (String) hm.get(NAME);
        if ("".equals(idString.trim()) && "".equals(name.trim())) {
            assignmentList = assignmentMapper.queryAssignmentsWithoutFilter(pagination);
        } else {
            try {
                int id = Integer.parseInt(idString.trim());
                // 如果有id直接返回assignmentid=id的作业
                assignmentList.add(assignmentMapper.getOneAssignment(id));
            } catch (NumberFormatException e) {
                assignmentList = assignmentMapper.queryAssignmentByName(pagination, name.trim());
            }
        }
        for (Assignment a : assignmentList) {
            a.setGroupList(groupMapper.getAssignmentGroup(a.getId()));
        }
        return assignmentList;
    }

    public EntityVO<Assignment> assignmentEntityVO(Pagination pagination) {
        pagination.setParameters();
        List<Assignment> assignmentList = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String idString = (String) hm.get(ID);
        String name = (String) hm.get(NAME);
        Integer count = 0;
        if ("".equals(idString.trim()) && "".equals(name.trim())) {
            assignmentList = assignmentMapper.queryAssignmentsWithoutFilter(pagination);
            count = assignmentMapper.queryAssignmentsWithoutFilterCounter(pagination);
        } else {
            try {
                int id = Integer.parseInt(idString.trim());
                Assignment a = assignmentMapper.getOneAssignment(id);
                if (a != null) {
                    assignmentList.add(a);
                    count = 1;
                }
            } catch (NumberFormatException e) {
                assignmentList = assignmentMapper.queryAssignmentByName(pagination, name.trim());
                count = assignmentMapper.queryAssignmentByNameCounter(pagination, name.trim());
            }
        }
        for (Assignment a : assignmentList) {
            a.setGroupList(groupMapper.getAssignmentGroup(a.getId()));
            a.setProblemList(problemMapper.oneAssignmentProblems(a.getId()));
        }
        return EntityVO.<Assignment>builder().entities(assignmentList).count(count).build();
    }
}
