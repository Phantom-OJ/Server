package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.AssignmentCount;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.po.Record;
import sustech.edu.phantom.dboj.entity.po.ResultCnt;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.form.stat.HomeStat;
import sustech.edu.phantom.dboj.form.stat.ProblemStat;

import java.util.List;

/**
 * @author Lori
 */
public interface RecordMapper {
    /**
     * 返回所有分页record列表
     *
     * @param pagination 分页信息
     * @return list of record
     */
    List<Record> queryRecord(Pagination pagination);

    /**
     * @param record
     * @return
     */
    int saveRecord(Record record);

    /**
     * 使记录失效
     *
     * @param id
     * @return
     */
    int invalidateRecord(int id);

    /**
     * @param pagination
     * @return
     */
    List<Record> getAllRecords(Pagination pagination);

    List<ProblemStat> getProblemResultSet(int id);

    /**
     * 得到problem的语言数据集
     *
     * @param id problem id
     * @return problem的数据集
     */
    List<ProblemStat> getProblemDialectSet(int id);

    List<ProblemStat> getUserDialectSet(int id);

    List<ProblemStat> getUserResultSet(int id);

    /**
     * 查询单个record
     *
     * @param id     record id
     * @return Record 对象
     */
    RecordDetail getOneRecord(int id);

    /**
     * 通过userid去查
     *
     * @param pagination 分页信息
     * @param userId     user id
     * @return list of records
     */
    List<Record> getRecordsOfOnePerson(Pagination pagination, int userId);

    /**
     * 通过userid assignment title去查
     *
     * @param pagination      分页信息
     * @param userId          user id
     * @param assignmentTitle assignment的标题
     * @return list of records
     */
    List<Record> getRecordsOfPersonByAssignment(Pagination pagination, int userId, String assignmentTitle);

    /**
     * 通过userid problem title去查
     *
     * @param pagination   分页信息
     * @param userId       user id
     * @param problemTitle problem的标题
     * @return list of records
     */
    List<Record> getRecordsOfPersonByProblem(Pagination pagination, int userId, String problemTitle);

    /**
     * 通过userid assignment title problem title去查
     *
     * @param pagination      分页信息
     * @param userId          user id
     * @param assignmentTitle assignment的标题
     * @param problemTitle    problem的标题
     * @return list of records
     */
    List<RecordDetail> getRecordsOfPersonByAssignmentAndProblem(Pagination pagination, int userId, String assignmentTitle, String problemTitle);

    Integer getRecordsOfPersonByAssignmentAndProblemCounter(Pagination pagination, int userId, String assignmentTitle, String problemTitle);

    /**
     * 通过assignment title去查
     *
     * @param pagination      分页信息
     * @param assignmentTitle assignment的标题
     * @return list of records
     */
    List<Record> getRecordsByAssignment(Pagination pagination, String assignmentTitle);

    /**
     * 通过problem title去查
     *
     * @param pagination   分页信息
     * @param problemTitle problem的标题
     * @return list of records
     */
    List<Record> getRecordsByProblem(Pagination pagination, String problemTitle);

    /**
     * 通过assignment title problem title去查
     *
     * @param pagination      分页信息
     * @param assignmentTitle assignment的标题
     * @param problemTitle    problem的标题
     * @return list of records
     */
    List<RecordDetail> getRecordsByAssignmentAndProblem(Pagination pagination, String assignmentTitle, String problemTitle);

    Integer getRecordsByAssignmentAndProblemCounter(Pagination pagination, String assignmentTitle, String problemTitle);

    Integer getUserIdByCodeId(int cid);

    /**
     * 判断一个用户是否解出某个题
     * @param uid user id
     * @param pid problem id
     * @return count (*) of AC
     */
    List<ResultCnt> isSolvedByUser(int uid, int pid);

    List<AssignmentCount> counterOneAssignmentAC(@Param("list") List<Problem> list);

    Integer counterOneAssignment(int aid);

    List<AssignmentCount> counterOneAssignmentNotAC(@Param("list") List<Problem> list);

    List<HomeStat> getHomeStat();
}
