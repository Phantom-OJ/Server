package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.JudgePoint;

import java.util.List;

public interface JudgePointMapper {
    /**
     * to find all the judge points of the problem
     * @param id problem id
     * @return a list of judge point of the problem
     */
    List<JudgePoint> getAllJudgePointsOfProblem(int id);

    Integer saveOneProblemJudgePoints(@Param("list") List<JudgePoint> list);
}
