package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.enumeration.ProblemSolved;
import sustech.edu.phantom.dboj.entity.po.Assignment;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.po.ResultCnt;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.mapper.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AssignmentService {
    private final static String ID = "id";
    private final static String NAME = "name";

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    RecordMapper recordMapper;

    /**
     * 返回一个作业
     * 包含几个problem
     * problem的tag
     *
     * @param aid    assignment id
     * @param isUser 是否登陆
     * @param userId user id
     * @return 一个 Assignment
     */
    public Assignment getOneAssignment(int aid, boolean isUser, int userId, boolean isAdmin,List<Integer> userGroup) {
        Assignment a = assignmentMapper.getOneAssignment(aid, isAdmin);
        List<Group>  tmp = groupMapper.getAssignmentGroup(aid);
        List<Integer> tmp2 = tmp.stream().map(Group::getId).collect(Collectors.toList());
        tmp2.retainAll(userGroup);
        if (tmp2.size() == 0 && !isAdmin) {
            return null;
        }
        List<Problem> problemList = problemMapper.oneAssignmentProblems(aid, isAdmin);
        setSolvedAndTags(problemList, isUser, userId, isAdmin);
        a.setProblemList(problemList);
        a.setGroupList(tmp);
        return a;
    }

    public EntityVO<Assignment> assignmentEntityVO(Pagination pagination, boolean isAdmin, List<Integer> userGroupList) {
        pagination.setParameters();
        List<Assignment> assignmentList = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String idString = (String) hm.get(ID);
        String name = (String) hm.get(NAME);
        Integer count = 0;
        if ("".equals(idString.trim()) && "".equals(name.trim())) {
            assignmentList = assignmentMapper.queryAssignmentsWithoutFilter(pagination, isAdmin);
            count = assignmentMapper.queryAssignmentsWithoutFilterCounter(isAdmin);
        } else {
            try {
                int id = Integer.parseInt(idString.trim());
                Assignment a = assignmentMapper.getOneAssignment(id, isAdmin);
                if (a != null) {
                    assignmentList.add(a);
                    count = 1;
                }
            } catch (NumberFormatException e) {
                boolean flag2 = "".equals(name.trim());
                assignmentList = assignmentMapper.queryAssignmentByName(pagination, name.trim(), flag2, isAdmin);
                count = assignmentMapper.queryAssignmentByNameCounter(name.trim(), flag2, isAdmin);
            }
        }
        List<Assignment> after = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            List<Group> tmp = groupMapper.getAssignmentGroup(assignment.getId());
            List<Integer> tmp2 = tmp.stream().map(Group::getId).collect(Collectors.toList());
            tmp2.retainAll(userGroupList);
            if (tmp2.size() != 0 || isAdmin) {
                assignment.setGroupList(tmp);
                assignment.setProblemList(problemMapper.oneAssignmentProblems(assignment.getId(), isAdmin));
                after.add(assignment);
            } else {
                --count;
            }
        }
        return EntityVO.<Assignment>builder().entities(after).count(count).build();
    }

    private void setSolvedAndTags(List<Problem> problemList, boolean isUser, int userId, boolean isAdmin) {
        for (Problem p : problemList) {
            p.setTagList(tagMapper.getProblemTags(p.getId()));
            if (!isAdmin) {
                p.setSolution(null);
            }
            if (isUser) {
                List<ResultCnt> tmp = recordMapper.isSolvedByUser(userId, p.getId());
                if (tmp.size() == 0) {
                    p.setSolved(ProblemSolved.NO_SUBMISSION);
                } else {
                    for (ResultCnt c : tmp) {
                        if ("AC".equalsIgnoreCase(c.getResult().trim())) {
                            p.setSolved(ProblemSolved.AC);
                            break;
                        } else {
                            p.setSolved(ProblemSolved.WA);
                        }
                    }
                }
            } else {
                p.setSolved(ProblemSolved.NO_SUBMISSION);
            }
        }
    }
}
