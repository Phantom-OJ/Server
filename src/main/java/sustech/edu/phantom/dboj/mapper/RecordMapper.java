package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.form.stat.ProblemStat;

import java.util.List;

public interface RecordMapper {
    List<Record> queryRecord(Pagination pagination);

    int saveRecord(Record record);

    /**
     * 使记录失效
     * @param id
     * @return
     */
    int invalidateRecord(int id);

    /**
     *
     * @param pagination
     * @return
     */
    List<Record> getAllRecords(Pagination pagination);

    List<ProblemStat> getProblemResultSet(int id);

    List<ProblemStat> getProblemDialectSet(int id);
}
