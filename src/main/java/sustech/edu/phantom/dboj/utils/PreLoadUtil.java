package sustech.edu.phantom.dboj.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sustech.edu.phantom.dboj.form.stat.HomeStat;
import sustech.edu.phantom.dboj.service.SchedulingService;
import sustech.edu.phantom.dboj.service.StatService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @project sqloj
 * @filename PreLoadUtil
 * @date 2020/12/13 00:13
 */
@Component
@Slf4j
public class PreLoadUtil {
    public static final String homeStatistics = "Home statistics";

    @Autowired
    StatService statService;

    @Autowired
    SchedulingService schedulingService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void loadHomeStat() {
        try {
            List<HomeStat> homeStats = statService.getHomeStat();
            redisTemplate.opsForValue().set(homeStatistics, new Gson().toJson(homeStats));
            log.info("Load home statistics into redis");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
