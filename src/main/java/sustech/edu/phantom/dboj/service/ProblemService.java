package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import sustech.edu.phantom.dboj.mapper.TagMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProblemService {
    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    TagMapper tagMapper;


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
