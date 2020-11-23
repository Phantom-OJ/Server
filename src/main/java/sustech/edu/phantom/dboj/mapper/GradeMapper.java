package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Grade;

public interface GradeMapper {

    Grade getOneGrade(int userId, int problemId);

    boolean updateOneGrade(int userId, int problemId,int score);

    boolean saveOneGrade(Grade grade);
}
