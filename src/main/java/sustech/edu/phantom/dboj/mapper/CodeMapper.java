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

    Code queryRecentCode(int userId, int problemId);
}
