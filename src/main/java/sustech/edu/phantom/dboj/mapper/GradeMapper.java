package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.po.Grade;
import sustech.edu.phantom.dboj.entity.vo.UserGrade;

import java.util.List;

public interface GradeMapper {

    Grade getOneGrade(int userId, int problemId);

    boolean updateOneGrade(int userId, int problemId,int score);

    boolean saveOneGrade(Grade grade);

    /**
     * 获取用户id为uid的成绩信息
     * @param uid user id
     * @return list of user grade
     */
    List<UserGrade> getUserGrade(int uid);
}
