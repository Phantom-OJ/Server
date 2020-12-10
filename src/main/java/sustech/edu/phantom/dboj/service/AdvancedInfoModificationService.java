package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sustech.edu.phantom.dboj.mapper.UserMapper;

import java.util.List;
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
    private final static String[] ROLE_LIST = {"ROLE_STUDENT", "ROLE_SA", "ROLE_TEACHER"};
    @Autowired
    UserMapper userMapper;

    public void grantUser(Map<String, List<Integer>> hm) {
        try {
            for (Map.Entry<String, List<Integer>> entry : hm.entrySet()) {
                if (ArrayUtils.contains(ROLE_LIST, entry.getKey())) {
                    userMapper.grantUser(entry.getKey(), entry.getValue());
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    break;
                }
            }
        } catch (Exception e) {
            log.error("something happens in the internal server.");
        }
    }
}
