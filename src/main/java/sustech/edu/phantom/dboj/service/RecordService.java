package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.RecordMapper;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    RecordMapper recordMapper;

    /**
     * 保存判题记录
     * @param record 待保存的记录
     * @return 1 为成功插入， 0 为不成功
     */
    public int saveRecord(Record record) {
        return recordMapper.saveRecord(record);
    }

    public List<Record> getRecordList(Pagination pagination) {
        return recordMapper.getAllRecords(pagination);
    }

    public Record getOneRecord(int id) {
        return null;
    }
}
