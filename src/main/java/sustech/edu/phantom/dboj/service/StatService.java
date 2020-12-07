package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.form.stat.ProblemStat;
import sustech.edu.phantom.dboj.form.stat.ProblemStatSet;
import sustech.edu.phantom.dboj.mapper.RecordMapper;

import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/7 17:12
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StatService {
    @Autowired
    RecordMapper recordMapper;


    /**
     * @param id problem id
     * @return
     */
    public ProblemStatSet getOneProblemStat(int id) {
        List<ProblemStat> reSet = null;
        List<ProblemStat> diSet = null;
        try {
            reSet = recordMapper.getProblemResultSet(id);
            diSet = recordMapper.getProblemDialectSet(id);
        } catch (Exception e) {
            log.error("Some errors happen in the database connection.");
        }
        return ProblemStatSet
                .builder()
                .resultSet(reSet)
                .dialectSet(diSet)
                .build();
    }

    public ProblemStatSet getOneUserStat(int id) {
        List<ProblemStat> reSet = null;
        List<ProblemStat> diSet = null;
        try {
            reSet = recordMapper.getUserResultSet(id);
            diSet = recordMapper.getUserDialectSet(id);
        } catch (Exception e) {
            log.error("Some errors happen in the database connection.");
        }
        return ProblemStatSet
                .builder()
                .resultSet(reSet)
                .dialectSet(diSet)
                .build();
    }

}
