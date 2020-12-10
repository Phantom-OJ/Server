package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.po.JudgeDatabase;

import java.util.List;

public interface JudgeDatabaseMapper {
    JudgeDatabase getJudgeDatabaseById(int id);

    /**
     * 保存一个判题数据库
     * @param judgeDatabase 判题数据库对象
     * @return 影响的行数
     */
    int saveJudgeDatabase(JudgeDatabase judgeDatabase);

    /**
     * 得到所有的判题数据库
     * @return 所有的判题数据库
     */
    List<JudgeDatabase> getAllJudgeDB();

    JudgeDatabase selJudgeDatabaseById(int id);
}
