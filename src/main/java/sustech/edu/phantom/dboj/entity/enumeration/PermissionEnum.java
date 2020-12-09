package sustech.edu.phantom.dboj.entity.enumeration;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/8 16:07
 */
public enum PermissionEnum {
    //sa
    VIEW_SUBMISSIONS("view all submissions"),
    PROVIDE_SOLUTION("provide the solution"),
    CREATE_ANNOUNCEMENT("publish the announcement"),
    VIEW_GROUPS("view all groups"),
    VIEW_ASSIGNMENTS("view all assignments"),
    VIEW_CODES("view all codes"),
    //teacher
    CREATE_GROUPS("create groups"),
    CREATE_ASSIGNMENT("create assignment"),//这里包含了创建问题, 判题点, 判题数据库, 判题脚本, 示例数据库
    MODIFY_ASSIGNMENT("modify assignment"),// 修改作业, 修改问题
    VIEW_JUDGE_DETAILS("view judge details"),//查看judge db, judge point, judge script
    GRANT("grant other users"),
    VIEW_PERMISSIONS("view all permissions");
    private final String detail;

    PermissionEnum(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
