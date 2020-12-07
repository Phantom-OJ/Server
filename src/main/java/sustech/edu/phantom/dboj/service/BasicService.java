package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.mapper.UserMapper;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 10:03
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BasicService {

    @Autowired
    UserMapper userMapper;

    public String getLastState(int id) {
        String state = null;
        try {
            state = userMapper.getState(id);
        } catch (Exception e) {
            log.error("There are some errors in the internal server.");
        }
        return state;
    }
}
