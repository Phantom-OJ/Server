package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.form.stat.ProblemStat;

import java.util.List;

public interface RecordMapper {
    /**
     * 返回所有分页record列表
     * @param pagination 分页信息
     * @return list of record
     */
    List<Record> queryRecord(Pagination pagination);

    /**
     *
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
}
