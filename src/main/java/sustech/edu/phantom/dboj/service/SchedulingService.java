package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sustech.edu.phantom.dboj.form.stat.HomeStat;
import sustech.edu.phantom.dboj.mapper.AssignmentMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/8 20:52
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SchedulingService {

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    ProblemMapper problemMapper;

    public void updateAssignmentInfo() {
        try {
            log.info("update the state of the assignment and problem");
            List<Integer> pubicList = assignmentMapper.get2BPublicId();
            List<Integer> closedList = assignmentMapper.get2BClosedId();
            assignmentMapper.publishAssignment(pubicList);
            assignmentMapper.closeAssignment(closedList);
            problemMapper.publishProblems(pubicList);
            problemMapper.closedProblems(closedList);
        } catch (Exception e) {
            log.error("Internal server error when updating the status of the assignment and problem.");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public List<HomeStat> getHomeStat() {
        return null;
    }
}
