package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.po.Record;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.entity.vo.RecordDetail;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.mapper.RecordMapper;
import sustech.edu.phantom.dboj.mapper.RecordProblemMapper;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RecordService {
    private final static String ASSIGNMENT = "assignment";
    private final static String PROBLEM = "problem";
    private final static String USER = "user";

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordProblemMapper recordProblemMapper;

    /**
     * 保存判题记录
     *
     * @param record 待保存的记录
     * @return 1 为成功插入， 0 为不成功
     */
    public int saveRecord(Record record) {
        return recordMapper.saveRecord(record);
    }
//
//    /**
//     * @param pagination 分页信息
//     * @return list of records
//     */
//    public List<Record> getRecordList(Pagination pagination) {
//        pagination.setParameters();
//        List<Record> records = new ArrayList<>();
//        HashMap<String, Object> hm = pagination.getFilter();
//        String usrname = (String) hm.get(USER);
//        if ("".equals(usrname.trim())) {
//            records = recordMapper.getRecordsByAssignmentAndProblem(pagination, (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
//        } else {
//            User tmp = userMapper.findUserByName(usrname);
//            if (tmp != null) {
//                records = recordMapper.getRecordsOfPersonByAssignmentAndProblem(pagination, tmp.getId(), (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
//            }
//        }
//        return records;
//    }

    public EntityVO<RecordDetail> getRecordDetailList(Pagination pagination) {
        pagination.setParameters();
        List<RecordDetail> records = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String usrname = (String) hm.get(USER);
        Integer counter = 0;
        if ("".equals(usrname.trim())) {
            records = recordMapper.getRecordsByAssignmentAndProblem(pagination, (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
            counter = recordMapper.getRecordsByAssignmentAndProblemCounter(pagination, (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
            for (RecordDetail record : records) {
                record.setDescription(recordProblemMapper.getOneRecordDetails(record.getId(), record.getProblemId()));
            }
        } else {
            User tmp = userMapper.findUserByName(usrname);
            if (tmp != null) {
                records = recordMapper.getRecordsOfPersonByAssignmentAndProblem(pagination, tmp.getId(), (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
                counter = recordMapper.getRecordsOfPersonByAssignmentAndProblemCounter(pagination, tmp.getId(), (String) hm.get(ASSIGNMENT), (String) hm.get(PROBLEM));
                for (RecordDetail record : records) {
                    record.setDescription(recordProblemMapper.getOneRecordDetails(record.getId(), record.getProblemId()));
                }
            }
        }
        return EntityVO.<RecordDetail>builder()
                .entities(records)
                .count(counter)
                .build();
    }

    /**
     * 得到具体record的代码等信息
     * @param id record id
     * @param userId user id
     * @param isAdmin 是否是管理员
     * @return record detail信息
     */
    public RecordDetail getOneRecord(int id, int userId, boolean isAdmin) {
        RecordDetail r = recordMapper.getOneRecord(id);
        if (!isAdmin || !r.getUserId().equals(userId)) {
            r = null;
            throw new RuntimeException();
        } else {
            r.setDescription(recordProblemMapper.getOneRecordDetails(id, r.getProblemId()));
            return r;
        }
    }


    public Integer getUserIdByCodeId(int cid) {
        return recordMapper.getUserIdByCodeId(cid);
    }
}
