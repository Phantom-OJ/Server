package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.JudgeDatabase;

import java.util.List;

public interface JudgeDatabaseMapper {
    JudgeDatabase getJudgeDatabaseById(int id);

    int saveJudgeDatabase(@Param("list") List<String> list);
}
