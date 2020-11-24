package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Problem;
import sustech.edu.phantom.dboj.entity.Tag;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.CodeMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import sustech.edu.phantom.dboj.mapper.TagMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProblemService {
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String TAG = "tags";

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagService tagService;

    @Autowired
    CodeMapper codeMapper;

    public Problem getOneProblem(int id) {
        Problem problem = problemMapper.queryCurrentProblem(id);
        problem.setTagList(tagMapper.getProblemTags(id));
        return problem;
    }

    /**
     * 这里相对于上面的方法是多了一个用户的代码
     *
     * @param id
     * @param userId
     * @return
     */
    public Problem getOneProblem(int id, int userId) {
        Problem problem = getOneProblem(id);
        problem.setRecentCode(codeMapper.queryRecentCode(userId, id));
        return problem;
    }

    /**
     * @param pagination
     * @return
     */
    public List<Problem> getProblemList(Pagination pagination) {
        pagination.setParameters();
        List<Problem> problemList = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String idString = (String) hm.get(ID);
        String name = (String) hm.get(NAME);
        String tagString = (String) hm.get(TAG);
        if ("".equals(idString.trim()) && "".equals(name.trim()) && "".equals(tagString.trim())){
            problemList = problemMapper.queryProblemWithoutFilter(pagination);
        } else{
            try {
                int id = Integer.parseInt(idString.trim());
                // 如果有id直接返回problemid=id的问题
                problemList.add(problemMapper.queryCurrentProblem(id));
            } catch (NumberFormatException e) {
                if ("".equals(name.trim()) && "".equals(tagString.trim())){
                    return null;
                } else if ("".equals(tagString.trim())) {
                    problemList = problemMapper.queryProblemsByName(pagination, name.trim());
                } else {
                    List<Integer> tags = getProblemTagsIdList(pagination);
                    problemList = problemMapper.queryProblemsByTagAndName(pagination, tags, name.trim());
                }
            }
        }
        for (Problem p : problemList) {
            p.setTagList(tagMapper.getProblemTags(p.getId()));
        }
        return problemList;
    }

    /**
     * @param pagination
     * @return
     */
    private List<Integer> getProblemTagsIdList(Pagination pagination) {
        HashMap<String, Integer> hm = tagService.getTagMap();
        String tmp = (String) pagination.getFilter().get(TAG);
        String[] t = tmp.split(" ");
        List<Integer> tags = new ArrayList<>();
        for (String s : t) {
            if (hm.get(s) != null) {
                tags.add(hm.get(s));
            }
        }
        return tags;
    }

    public EntityVO<Problem> problemEntityVO(Pagination pagination) {
        pagination.setParameters();
        List<Problem> problemList = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String idString = (String) hm.get(ID);
        String name = (String) hm.get(NAME);
        String tagString = (String) hm.get(TAG);
        Integer count = 0;
        if ("".equals(idString.trim()) && "".equals(name.trim()) && "".equals(tagString.trim())){
            problemList = problemMapper.queryProblemWithoutFilter(pagination);
            count = problemMapper.queryProblemWithoutFilterCounter(pagination);
        } else{
            try {
                int id = Integer.parseInt(idString.trim());
                // 如果有id直接返回problemid=id的问题
                Problem p = problemMapper.queryCurrentProblem(id);
                if (p != null) {
                    problemList.add(p);
                    count = 1;
                }
            } catch (NumberFormatException e) {
                if ("".equals(name.trim()) && "".equals(tagString.trim())){
                    return null;
                } else if ("".equals(tagString.trim())) {
                    problemList = problemMapper.queryProblemsByName(pagination, name.trim());
                    count = problemMapper.queryProblemsByNameCounter(pagination, name.trim());
                } else {
                    List<Integer> tags = getProblemTagsIdList(pagination);
                    problemList = problemMapper.queryProblemsByTagAndName(pagination, tags, name.trim());
                    count = problemMapper.queryProblemsByTagAndNameCounter(pagination, tags, name.trim());
                }
            }
        }
        for (Problem p : problemList) {
            p.setTagList(tagMapper.getProblemTags(p.getId()));
        }
        return EntityVO.<Problem>builder().entities(problemList).count(count).build();
    }
}
