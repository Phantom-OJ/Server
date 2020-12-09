package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.po.JudgeDatabase;

import java.util.List;

public interface JudgeDatabaseMapper {
    JudgeDatabase getJudgeDatabaseById(int id);

    int saveJudgeDatabase(JudgeDatabase judgeDatabase);

    List<JudgeDatabase> getAllJudgeDB();

    JudgeDatabase selJudgeDatabaseById(int id);
}
