package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Code;

public interface CodeMapper {
    /**
     * 将code插入code表中
     * @param code code对象
     * @return 影响的行数
     */
    int saveCode(Code code);

    /**
     *
     * @param id
     * @return
     */
    Code queryCode(int id);

    int invalidateCode(int id);

    /**
     * 取一个用户最近一次提交的代码
     * @param userId user id
     * @param problemId problem id
     * @return Code 对象
     */
    String queryRecentCode(int userId, int problemId);
}
