package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Tag;
import sustech.edu.phantom.dboj.mapper.TagMapper;

import java.util.HashMap;
import java.util.List;

@Service
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
