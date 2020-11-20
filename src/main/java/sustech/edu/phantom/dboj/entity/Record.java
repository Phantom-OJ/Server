package sustech.edu.phantom.dboj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 一个名字 一个头像
//不给code
//Problem
public class Record {
    private Integer id;
    private Integer codeId;
    private Integer userId;
    private Integer problemId;
    private Integer score;
    private String result;
    private String description;
    private Long space;
    private Long time;
    private String dialect;
    private Integer codeLength;
    private Long submitTime;
//    private Boolean valid;
    private String problemTitle;
    private String avatar;
    private String username;
    private String code;
}
