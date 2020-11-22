package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.form.Pagination;
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

    /**
     * 查询单个record
     *
     * @param id     record id
     * @param userId user id
     * @return Record 对象
     */
    Record getOneRecord(int id, int userId);

    /**
     * 通过userid去查
     * @param pagination 分页信息
     * @param userId user id
     * @return list of records
     */
    List<Record> getRecordsOfOnePerson(Pagination pagination, int userId);

    /**
     * 通过userid assignment title去查
     * @param pagination 分页信息
     * @param userId user id
     * @param assignmentTitle assignment的标题
     * @return list of records
     */
    List<Record> getRecordsOfPersonByAssignment(Pagination pagination, int userId, String assignmentTitle);

    /**
     * 通过userid problem title去查
     * @param pagination 分页信息
     * @param userId user id
     * @param problemTitle problem的标题
     * @return list of records
     */
    List<Record> getRecordsOfPersonByProblem(Pagination pagination, int userId, String problemTitle);

    /**
     * 通过userid assignment title problem title去查
     * @param pagination 分页信息
     * @param userId user id
     * @param assignmentTitle assignment的标题
     * @param problemTitle problem的标题
     * @return list of records
     */
    List<Record> getRecordsOfPersonByAssignmentAndProblem(Pagination pagination, int userId, String assignmentTitle, String problemTitle);

    /**
     * 通过assignment title去查
     * @param pagination 分页信息
     * @param assignmentTitle assignment的标题
     * @return list of records
     */
    List<Record> getRecordsByAssignment(Pagination pagination, String assignmentTitle);

    /**
     * 通过problem title去查
     * @param pagination 分页信息
     * @param problemTitle problem的标题
     * @return list of records
     */
    List<Record> getRecordsByProblem(Pagination pagination, String problemTitle);

    /**
     * 通过assignment title problem title去查
     * @param pagination 分页信息
     * @param assignmentTitle assignment的标题
     * @param problemTitle problem的标题
     * @return list of records
     */
    List<Record> getRecordsByAssignmentAndProblem(Pagination pagination, String assignmentTitle, String problemTitle);
}
