package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sustech.edu.phantom.dboj.entity.po.Group;
import sustech.edu.phantom.dboj.entity.po.JudgePoint;
import sustech.edu.phantom.dboj.entity.po.Permission;
import sustech.edu.phantom.dboj.entity.po.Tag;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.form.upload.UploadAssignmentForm;
import sustech.edu.phantom.dboj.form.upload.UploadProblemForm;
import sustech.edu.phantom.dboj.mapper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 01:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AdvancedInfoModificationService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    AnnouncementMapper announcementMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    JudgePointMapper judgePointMapper;

    @Autowired
    TagMapper tagMapper;


    public void grantUser(Map<String, List<Integer>> hm) {
        var roleList = permissionMapper.getPermissions().stream().map(Permission::getRole).distinct()
                .collect(Collectors.toList());
        try {
            for (Map.Entry<String, List<Integer>> entry : hm.entrySet()) {
                if (roleList.contains(entry.getKey())) {
                    userMapper.grantUser(entry.getKey(), entry.getValue());
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    break;
                }
            }
        } catch (Exception e) {
            log.error("something happens in the internal server.");
        }
    }

    public int modifyPermission(Permission permission, boolean delete, int id) {
        Integer flag;
        if (delete) {
            flag = permissionMapper.invalidatePermission(id);
        } else {
            Integer idx = permissionMapper.getPermission(permission.getRole(), permission.getAllowance());
            flag = (idx == null) ? permissionMapper.savePermission(permission) : permissionMapper.validatePermission(idx);
        }
        return flag;
    }

    public int modifyGroup(String description, boolean delete, int id) {
        if (delete) {
            groupMapper.invalidateGroupAssignment(id);
            groupMapper.invalidateGroupUser(id);
            return groupMapper.invalidateGroup(id);
        } else {
            return groupMapper.saveGroup(description);
        }
    }

    public boolean modifyAnnouncement(UploadAnnouncementForm form, Integer id) {
        return announcementMapper.modifyAnnouncement(form, id);
    }

    public boolean deleteAnnouncement(Integer id) {
        return announcementMapper.deleteAnnouncement(id) != 0;
    }

    public boolean modifyProblem(int pid, UploadProblemForm form) {
        return problemMapper.modifyProblem(pid, form) != 0;
    }

    public boolean modifyAssignment(int aid, UploadAssignmentForm form) {
        return assignmentMapper.modifyAssignment(aid, form);
    }

    public List<JudgePoint> getOneProblemJudgePoint(int id) {
        return judgePointMapper.getAllJudgePointsOfProblem(id);
    }

    public boolean saveJudgePoint(JudgePoint judgePoint) {
        List<JudgePoint> judgePoints = new ArrayList<>();
        judgePoints.add(judgePoint);
        return judgePointMapper.saveOneProblemJudgePoints(judgePoints) != 0;
    }

    public boolean deleteJudgePoint(int id) {
        return judgePointMapper.invalidateJudgePoint(id) != 0;
    }

    public boolean modifyJudgePoint(int id, JudgePoint judgePoint) {
        return judgePointMapper.modifyJudgePoint(id, judgePoint) != 0;
    }

    public UploadAssignmentForm getAssignmentForm(int aid) {
        UploadAssignmentForm form = assignmentMapper.getAssign(aid);
        List<UploadProblemForm> uploadProblemFormList = problemMapper.getProblem(aid);
        List<Integer> groupList = groupMapper.getAssignmentGroup(aid).stream().map(Group::getId).collect(Collectors.toList());
        form.setGroupList(groupList);
        for (UploadProblemForm f : uploadProblemFormList) {
            f.setJudgePointList(judgePointMapper.getJudgePoints(f.getId()));
            f.setTagList(tagMapper.getProblemTags(f.getId()).stream().map(Tag::getId).collect(Collectors.toList()));
        }
        form.setUploadProblemFormList(uploadProblemFormList);
        return form;
    }
}
