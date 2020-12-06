package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.entity.JudgePoint;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadAssignmentForm;
import sustech.edu.phantom.dboj.form.upload.UploadJudgePointForm;
import sustech.edu.phantom.dboj.form.upload.UploadProblemForm;
import sustech.edu.phantom.dboj.mapper.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UploadService {

    @Autowired
    AnnouncementMapper announcementMapper;

    @Autowired
    JudgeDatabaseMapper judgeDatabaseMapper;

    @Autowired
    JudgeScriptMapper judgeScriptMapper;

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    JudgePointMapper judgePointMapper;

    @Autowired
    TagMapper tagMapper;

    public boolean saveAnnouncement(UploadAnnouncementForm form) {
        int flag = 0;
        try {
            flag = announcementMapper.insertOneAnnouncement(form);
            if (flag == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (Exception e) {
            log.error("");
        }

        return flag != 0;
    }

    public boolean saveJudgeDB(List<String> list) {
        try {
            int a = judgeDatabaseMapper.saveJudgeDatabase(list);
            if (a > 0) {
                log.info("Insert " + a + " records into judge database");
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("Inconsistency occurs in saving judge database");
                return false;
            }
        } catch (Exception e) {
            log.error("Exception occurs in saving judge database");
            return false;
        }
    }

    public boolean saveJudgeScript(List<String> list) {
        try {
            int a = judgeScriptMapper.saveJudgeScript(list);
            if (a > 0) {
                log.info("Insert " + a + " records into judge script");
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("Inconsistency occurs in saving judge script");
                return false;
            }
        } catch (Exception e) {
            log.error("Exception occurs in saving judge script");
            return false;
        }
    }

    /**
     * 添加整个assignment是一个整体，所以只要其中一个出现了问题就全部rollback
     * @param form 上传作业的表单
     * @return 是否添加成功
     */
    public boolean saveAssignment(UploadAssignmentForm form) {
        Assignment a = Assignment
                .builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .startTime(form.getStartTime())
                .endTime(form.getEndTime())
                .status(form.getStatus())
                .fullScore(form.getFullScore())
                .sampleDatabasePath(form.getSampleDatabasePath())
                .build();
        try {
            if (assignmentMapper.saveAssignment(a) > 0) {
                int aid = a.getId();
                for (UploadProblemForm p : form.getUploadProblemFormList()
                ) {
                    //TODO: tag not set
                    Problem tmp = Problem
                            .builder()
                            .assignmentId(aid)
                            .title(p.getTitle())
                            .description(p.getDescription())
                            .fullScore(p.getFullScore())
                            .spaceLimit(p.getSpaceLimit())
                            .timeLimit(p.getTimeLimit())
                            .indexInAssignment(p.getIndexInAssignment())
                            .solution(p.getSolution())
                            .status(a.getStatus())
                            .type(p.getType())
                            .build();

                    if (problemMapper.saveProblem(tmp) > 0) {
                        List<Integer> list = p.getTagList();
                        if (tagMapper.saveOneProblemTags(list, tmp.getId()) == 0) {
                            //:ROLLBACK
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return false;
                        }
                        List<JudgePoint> judgePoints = new ArrayList<>();
                        for (UploadJudgePointForm j : p.getJudgePointList()
                        ) {
                            judgePoints.add(new JudgePoint(j));
                        }
                        if (judgePointMapper.saveOneProblemJudgePoints(judgePoints) == 0) {
                            //: ROLLBACK
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    } else {
                        //: handling rollback
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return false;
                    }
                }
            } else {
                log.error("No saving assignment.");
                return false;
            }
        } catch (Exception e) {
            log.error("Something wrong with saving assignment.");
            return false;
        }

        return true;
    }
}
