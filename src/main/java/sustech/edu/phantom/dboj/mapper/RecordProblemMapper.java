package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.RecordProblemJudgePoint;

import java.util.List;

/**
 * @author Lori
 */

public interface RecordProblemMapper {
    /**
     * 获取一个record的所有判题点的数据
     * @param recordId record id
     * @param problemId problem id
     * @return list
     */
    List<RecordProblemJudgePoint> getOneRecordDetails(int recordId, int problemId);

    /**
     * 将判题点的数据存入表中
     * @param judgePointList list
     * @return int表示插入的行数
     */
    int saveOneRecordDetails(@Param("judgePointList") List<RecordProblemJudgePoint> judgePointList);
}
