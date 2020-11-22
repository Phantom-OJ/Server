package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Record;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.form.stat.ProblemStatSet;
import sustech.edu.phantom.dboj.mapper.RecordMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecordService {
    private final static String ASSIGNMENT = "assignment";
    private final static String PROBLEM = "problem";
    private final static String USER = "user";

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 保存判题记录
     *
     * @param record 待保存的记录
     * @return 1 为成功插入， 0 为不成功
     */
    public int saveRecord(Record record) {
        return recordMapper.saveRecord(record);
    }

    /**
     * @param pagination
     * @return
     */
    public List<Record> getRecordList(Pagination pagination) {
        pagination.setParameters();
        List<Record> records = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String usrname = (String) hm.get(USER);
        if ("".equals(usrname.trim())) {
            records = recordMapper.getRecordsByAssignmentAndProblem(pagination, (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
        } else {
            User tmp = userMapper.findUserByName(usrname);
            if (tmp != null) {
                records = recordMapper.getRecordsOfPersonByAssignmentAndProblem(pagination, tmp.getId(), (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
            }
        }
        return records;
    }

    /**
     * @param id
     * @param userId
     * @return
     */
    public Record getOneRecord(int id, int userId) {
        return recordMapper.getOneRecord(id, userId);
    }

    /**
     * @param id problem id
     * @return
     */
    public ProblemStatSet getOneProblemStat(int id) {
        return ProblemStatSet
                .builder()
                .resultSet(recordMapper.getProblemResultSet(id))
                .dialectSet(recordMapper.getProblemDialectSet(id))
                .build();
    }
}
