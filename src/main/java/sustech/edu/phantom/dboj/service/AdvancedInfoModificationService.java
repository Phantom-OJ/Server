package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.Map;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 01:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AdvancedInfoModificationService {
    @Autowired
    UserMapper userMapper;

    public void grantUser(Map<String,Object> hm) {

    }
}
