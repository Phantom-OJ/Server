package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.JudgePoint;

import java.util.List;

public interface JudgePointMapper {
    /**
     * to find all the judge points of the problem
     * @param id problem id
     * @return a list of judge point of the problem
     */
    List<JudgePoint> getAllJudgePointsOfProblem(int id);
}
