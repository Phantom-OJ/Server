package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.enumeration.ProblemSolved;
import sustech.edu.phantom.dboj.entity.po.Problem;
import sustech.edu.phantom.dboj.entity.po.ResultCnt;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.mapper.CodeMapper;
import sustech.edu.phantom.dboj.mapper.ProblemMapper;
import sustech.edu.phantom.dboj.mapper.RecordMapper;
import sustech.edu.phantom.dboj.mapper.TagMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
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

    @Autowired
    RecordMapper recordMapper;

    public Problem getOneProblem(int id, boolean isAdmin) {
        Problem problem = problemMapper.queryCurrentProblem(id, isAdmin);
        // 管理員或者closed了
        if (isAdmin || "closed".equalsIgnoreCase(problem.getStatus().trim())) {

        } else {
            problem.setSolution(null);
        }
        problem.setTagList(tagMapper.getProblemTags(id));
        return problem;
    }

    public Long getSimpleProblem(int id) {
        return problemMapper.queryTime(id);
    }

    /**
     * 这里相对于上面的方法是多了一个用户的代码
     *
     * @param id     problem id
     * @param userId user id
     * @return problem 对象
     */
    public Problem getOneProblem(int id, int userId, boolean isAdmin) {
        Problem problem = getOneProblem(id, isAdmin);
        problem.setRecentCode(codeMapper.queryRecentCode(userId, id));
        return problem;
    }


    /**
     * @param pagination 分页过滤信息
     * @return group
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


    /**
     * 获取problem列表
     * <br></br>
     * 如果有用户要额外显示是否已经AC
     *
     * @param pagination 分页筛选
     * @param isUser     是否有用户登录
     * @return 包装类
     */
    public EntityVO<Problem> problemEntityVO(Pagination pagination, boolean isUser, int userId, boolean isAdmin) {
        pagination.setParameters();
        List<Problem> problemList = new ArrayList<>();
        HashMap<String, Object> hm = pagination.getFilter();
        String idString = (String) hm.get(ID);
        String name = (String) hm.get(NAME);
        String tagString = (String) hm.get(TAG);
        Integer count = 0;
        if ("".equals(idString.trim()) && "".equals(name.trim()) && "".equals(tagString.trim())) {
            problemList = problemMapper.queryProblemWithoutFilter(pagination, isAdmin);
            count = problemMapper.queryProblemWithoutFilterCounter(pagination, isAdmin);
        } else {
            try {
                int id = Integer.parseInt(idString.trim());
                // 如果有id直接返回problemid=id的问题
                Problem p = problemMapper.queryCurrentProblem(id, isAdmin);
                if (p != null) {
                    problemList.add(p);
                    count = 1;
                }
            } catch (NumberFormatException e) {
                if ("".equals(name.trim()) && "".equals(tagString.trim())) {
                    //TODO:更正
                    throw new RuntimeException("id format wrong");
                } else if ("".equals(tagString.trim())) {
                    problemList = problemMapper.queryProblemsByName(pagination, name.trim(), isAdmin);
                    count = problemMapper.queryProblemsByNameCounter(pagination, name.trim(), isAdmin);
                } else {
                    List<Integer> tags = getProblemTagsIdList(pagination);
                    boolean flag2 = "".equals(name.trim());
                    problemList = problemMapper.queryProblemsByTagAndName(pagination, tags, name.trim(), flag2, isAdmin);
                    count = problemMapper.queryProblemsByTagAndNameCounter(tags, name.trim(), flag2, isAdmin);
                }
            }
        }
        setSolvedAndTags(problemList, isUser, userId, isAdmin);
        return EntityVO.<Problem>builder().entities(problemList).count(count).build();
    }

    private void setSolvedAndTags(List<Problem> problemList, boolean isUser, int userId, boolean isAdmin) {
        for (Problem p : problemList) {
            p.setTagList(tagMapper.getProblemTags(p.getId()));
            if (!isAdmin) {
                p.setSolution(null);
            }
            if (isUser) {
                List<ResultCnt> tmp = recordMapper.isSolvedByUser(userId, p.getId());
                if (tmp.size() == 0) {
                    p.setSolved(ProblemSolved.NO_SUBMISSION);
                } else {
                    for (ResultCnt c : tmp) {
                        if ("AC".equalsIgnoreCase(c.getResult().trim())) {
                            p.setSolved(ProblemSolved.AC);
                            break;
                        } else {
                            p.setSolved(ProblemSolved.WA);
                        }
                    }
                }
            } else {
                p.setSolved(ProblemSolved.NO_SUBMISSION);
            }
        }
    }
}
