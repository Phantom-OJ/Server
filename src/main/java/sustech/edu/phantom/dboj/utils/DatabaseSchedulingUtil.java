package sustech.edu.phantom.dboj.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import sustech.edu.phantom.dboj.mapper.AssignmentMapper;

/**
 * 这个类是springboot定时任务
 */
public class DatabaseSchedulingUtil {
    /**
     * 每隔三十分钟更新一遍assignment表
     */
    @Autowired
    AssignmentMapper assignmentMapper;

    @Scheduled(cron = "0 */30 * * * ?")
    public void updateAssignmentInfo() {

    }
}
