package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.po.AssignmentCount;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.vo.UserGrade;
import sustech.edu.phantom.dboj.form.stat.*;
import sustech.edu.phantom.dboj.mapper.GradeMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import sustech.edu.phantom.dboj.mapper.RecordMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/7 17:12
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StatService {
    @Autowired
    RecordMapper recordMapper;

    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    ProblemMapper problemMapper;


    /**
     * @param id problem id
     * @return 问题结果集合
     */
    public ProblemStatSet getOneProblemStat(int id) {
        List<ProblemStat> reSet = null;
        List<ProblemStat> diSet = null;
        try {
            reSet = recordMapper.getProblemResultSet(id);
            diSet = recordMapper.getProblemDialectSet(id);
        } catch (Exception e) {
            log.error("Some errors happen in the database connection.");
        }
        return ProblemStatSet
                .builder()
                .resultSet(reSet)
                .dialectSet(diSet)
                .build();
    }

    /**
     * 获取一个用户的所有提交记录
     *
     * @param id 用户 id
     * @return 结果数据集
     */
    public ProblemStatSet getOneUserStat(int id) {
        List<ProblemStat> reSet = null;
        List<ProblemStat> diSet = null;
        try {
            reSet = recordMapper.getUserResultSet(id);
            diSet = recordMapper.getUserDialectSet(id);
        } catch (Exception e) {
            log.error("Some errors happen in the database connection.");
        }
        return ProblemStatSet
                .builder()
                .resultSet(reSet)
                .dialectSet(diSet)
                .build();
    }

    /**
     * 获取用户成绩记录
     *
     * @param uid user id
     * @return 用户成绩
     * //TODO: group没有去掉
     */
    public List<UserGrade> getUserGrade(int uid) {
        return gradeMapper.getUserGrade(uid);
    }

    public List<AssignmentStat> getOneAssignmentStat(int aid) {
        List<Problem> problemList = problemMapper.oneAssignmentProblems(aid);
        List<AssignmentCount> ACList = recordMapper.counterOneAssignmentAC(problemList);
        List<AssignmentCount> NotACList = recordMapper.counterOneAssignmentNotAC(problemList);
        Integer total = recordMapper.counterOneAssignment(aid);
        List<AssignmentStat> assignmentStats = new ArrayList<>();
        for (Problem problem : problemList) {
            assignmentStats.add(AssignmentStat.builder().problemId(problem.getId()).ac(0).wa(0).problemTitle(problem.getTitle()).total(total).build());
        }
        for (AssignmentCount a : ACList) {
            for (AssignmentStat assignmentStat : assignmentStats) {
                if (assignmentStat.getProblemId().equals(a.getProblemId())) {
                    assignmentStat.setAc(a.getCount());
                    break;
                }
            }
        }
        for (AssignmentCount a : NotACList) {
            for (AssignmentStat assignmentStat : assignmentStats) {
                if (assignmentStat.getProblemId().equals(a.getProblemId())) {
                    assignmentStat.setWa(a.getCount());
                    break;
                }
            }
        }
        for (AssignmentStat a : assignmentStats) {
            a.setNoSubmission();
        }
        return assignmentStats;
    }

    /**
     *
     * @return
     */
    public List<HomeStat> getHomeStat() {
        return recordMapper.getHomeStat();
    }

    public List<AssignmentScore> getOneAssignmentScore(int aid) {
        return gradeMapper.getOneAssignmentScore(aid);
    }
}
