package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.po.*;
import sustech.edu.phantom.dboj.mapper.*;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename RequireService
 * @date 2020/12/10 16:05
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RequireService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    JudgeDatabaseMapper judgeDatabaseMapper;

    @Autowired
    JudgeScriptMapper judgeScriptMapper;

    @Autowired
    TagMapper tagMapper;

    /**
     * 返回给前端的接口
     * 获取所有的judge database信息
     * 只有 id 和 keyword
     *
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
     * 返回给前端的接口
     * 根据id筛选具体的judge database
     * 只有 id 和 keyword
     *
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


    public List<Permission> getPermissionList() {
        List<Permission> permissionList;
        permissionList = permissionMapper.getPermissions();
        return permissionList;
    }

    public List<User> getUsers(String filter) throws Exception{
        return userMapper.findUserByFilter(filter);
    }


    public List<User> getUsersByGroup(int gid) throws Exception{
        return userMapper.findUserByGroup(gid);
    }

    public List<User> getUserNotInGroup(int gid) {
        return userMapper.findNotUserByGroup(gid);
    }

    public List<Tag> getTag() {
        return tagMapper.allTagList();
    }

}
