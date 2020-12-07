package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sustech.edu.phantom.dboj.entity.*;
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
    UserMapper userMapper;

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

    /**
     * 上传公告
     *
     * @param form 公告表单
     * @return 是否成功
     */
    public boolean saveAnnouncement(UploadAnnouncementForm form) {
        int flag = 0;
        try {
            flag = announcementMapper.insertOneAnnouncement(form);
            if (flag == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (Exception e) {
            log.error("Something wrong with saving announcement.");
        }

        return flag != 0;
    }

    /**
     * 上传judge database
     *
     * @param judgeDatabase judge database
     * @return 是否成功
     */
    public boolean saveJudgeDB(JudgeDatabase judgeDatabase) {
        try {
            int a = judgeDatabaseMapper.saveJudgeDatabase(judgeDatabase);
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

    /**
     * 返回给前端的接口
     * 根据id筛选具体的judge database
     * 只有 id 和 keyword
     * @param id judge database id
     * @return judge database 对象
     */
    public JudgeDatabase selOneDB(int id) {
        JudgeDatabase j = null;
        try {
            j = judgeDatabaseMapper.selJudgeDatabaseById(id);
        } catch (Exception e) {
            log.error("something wrong with selOneDB");
        }
        return j;
    }

    /**
     * 返回给前端的接口
     * 获取所有的judge database信息
     * 只有 id 和 keyword
     * @return 所有的judge database
     */
    public List<JudgeDatabase> getAllJudgeDB() {
        List<JudgeDatabase> list = null;
        try {
            list = judgeDatabaseMapper.getAllJudgeDB();
        } catch (Exception e) {
            log.error("something wrong with getAllJudgeDB");
        }
        return list;
    }

    /**
     * 上传judge script
     *
     * @param judgeScript judge script 对象
     * @return 是否成功
     */
    public boolean saveJudgeScript(JudgeScript judgeScript) {
        try {
            int a = judgeScriptMapper.saveJudgeScript(judgeScript);
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
     * 返回给前端的接口
     * 获取所有的judge script
     * 只有 id 和 keyword
     *
     * @return judge scripts
     */
    public List<JudgeScript> getAllJudgeScript() {
        List<JudgeScript> list = null;
        try {
            list = judgeScriptMapper.getAllJudgeScript();
        } catch (Exception e) {
            log.error("something wrong with getAllJudgeDB");
        }
        return list;
    }

    /**
     * 返回给前端的接口
     * 获取具体的judge script
     *
     * @param id judge script id
     * @return judge script 信息
     */
    public JudgeScript selOneJScript(int id) {
        JudgeScript j = null;
        try {
            j = judgeScriptMapper.selOneJudgeScript(id);
        } catch (Exception e) {
            log.error("something wrong with selOneJScript");
        }
        return j;
    }

    /**
     * 添加整个assignment是一个整体，所以只要其中一个出现了问题就全部rollback
     *
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

    /**
     * 上传头像
     *
     * @param filePath 图片路径
     * @param uid user id
     * @return 是否成功
     */
    public boolean uploadAvatar(String filePath, int uid) {
        try {
            int a = userMapper.uploadAvatar(filePath, uid);
            if (a > 0) {
                log.info("update avatar successfully");
                return true;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error("Inconsistency occurs in updating avatar");
                return false;
            }
        } catch (Exception e) {
            log.error("Exception occurs in updating avatar");
            return false;
        }
    }
}
