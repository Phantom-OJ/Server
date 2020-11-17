package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import sustech.edu.phantom.dboj.mapper.TagMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProblemService {
    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagService tagService;

    /**
     * 多个tag筛选标签
     * @param tags tags数组
     * @return List of problems
     */
    public List<Problem> getProblemByTag(String... tags) {
        HashMap<String, Integer> hm = tagService.getTagMap();
        List<Problem> problemList = new ArrayList<>();
        List<Integer> tagList = new ArrayList<>();
        for (String t : tags) {
            tagList.add(hm.get(t));
        }
        return null;
    }

    public Problem getOneProblem(int id) {
        return problemMapper.queryCurrentProblem(id);
    }

    public Problem getOneProblemOfOneUser(int id, int userId) {
        Problem problem = getOneProblem(id);
        return null;
    }

    public List<Problem> getProblemList(Pagination pagination) {
        return problemMapper.queryProblem(pagination);
    }
    //这里我的想法是通过map得到tid，然后直接sql语句筛选，filter的范围是什么，是只有tag还是有其他的？
//    public List<Problem> getProblemPage(Pagination pagination) {
//        Map<String, Integer> tagMap = tagMapper.getAllTags();
//        pagination.setParameters();
//        String[] filters = pagination.getFilter().trim().split(" ");
//        for (String tmp :
//                filters) {
//            tagMap.get(tmp);
//        }
//        List<Problem> tmp = problemMapper.queryProblem(pagination);
//        for (Problem p : tmp) {
//            p.setTags(problemMapper.findProblemTags(p));
//        }
//        return tmp;
//    }

}
