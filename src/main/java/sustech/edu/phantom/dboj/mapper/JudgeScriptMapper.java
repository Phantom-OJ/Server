package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.JudgeScript;

import java.util.List;

public interface JudgeScriptMapper {
    JudgeScript getOneJudgeScript(int id);

    int saveJudgeScript(JudgeScript judgeScript);

    List<JudgeScript> getAllJudgeScript();

    JudgeScript selOneJudgeScript(int id);
}
