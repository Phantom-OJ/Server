package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.JudgePoint;
import sustech.edu.phantom.dboj.form.upload.UploadJudgePointForm;

import java.util.List;

public interface JudgePointMapper {
    /**
     * to find all the judge points of the problem
     * @param id problem id
     * @return a list of judge point of the problem
     */
    //TODO:更改数据库取出值
    List<JudgePoint> getAllJudgePointsOfProblem(int id);
//TODO:更改数据库取出
    Integer saveOneProblemJudgePoints(@Param("list") List<JudgePoint> list);

    //TODO:更改数据库取出值
    List<JudgePoint> getAllJudgePointsOfProblemWithDialect(int id,String dialect);

    int invalidateJudgePoint(int id);

    int invalidJudgePointOneAssignment(@Param("pids") List<Integer> pids);


    int modifyJudgePoint(@Param("id") int id, @Param("param2") JudgePoint param2);

    List<UploadJudgePointForm> getJudgePoints(int pid);
}
