package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.po.Tag;
import sustech.edu.phantom.dboj.mapper.TagMapper;

import java.util.HashMap;
import java.util.List;
/**
 * @author Shilong Li (Lori)
 * @version 1.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TagService {
    @Autowired
    TagMapper tagMapper;

    public HashMap<String, Integer> getTagMap() {
        List<Tag> tagList = tagMapper.allTagList();
        HashMap<String, Integer> m = new HashMap<>();
        for (Tag t : tagList) {
            m.put(t.getKeyword(), t.getId());
        }
        return m;
    }


}
