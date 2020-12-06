package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.JudgeScript;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface JudgeScriptMapper {
    JudgeScript getOneJudgeScript(int id);

    int saveJudgeScript(@Param("list") List<String> list);

    List<JudgeScript> getAllJudgeScript(Pagination pagination);

}
