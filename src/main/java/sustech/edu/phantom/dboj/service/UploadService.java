package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadJudgePointForm;
import sustech.edu.phantom.dboj.mapper.*;

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

    public boolean saveAnnouncement(UploadAnnouncementForm form) {
        int flag = 0;
        try {
            flag = announcementMapper.insertOneAnnouncement(form);
        } catch (Exception e) {
            log.error("");
        }

        return flag != 0;
    }

    public boolean saveJudgeDB(List<String> list) {
        try {
            int a = judgeDatabaseMapper.saveJudgeDatabase(list);
            log.info("Insert " + a + " records into judge database");
            return true;
        } catch (Exception e) {
            log.error("Exception occurs in saving judge database");
            return false;
        }
    }

    public boolean saveJudgeScript(List<String> list) {
        try {
            int a = judgeScriptMapper.saveJudgeScript(list);
            log.info("Insert " + a + " records into judge database");
            return true;
        } catch (Exception e) {
            log.error("Exception occurs in saving judge script");
            return false;
        }
    }

    public boolean saveJudgePoint(List<UploadJudgePointForm> list) {

        return false;
    }
}
