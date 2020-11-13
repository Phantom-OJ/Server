package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface RecordMapper {
    List<Record> queryRecord(Pagination pagination);
}
