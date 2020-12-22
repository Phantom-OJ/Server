package sustech.edu.phantom.dboj.entity.po;

import lombok.*;

/**
 * @author Lori
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record {
    private Integer id;
    private Integer codeId;
    private Integer userId;
    private Integer problemId;
    private Integer score;
    private String result;
//    private String description;
    private Long space;
    private Long time;
    private String dialect;
    private Integer codeLength;
    private Long submitTime;
    private String problemTitle;
    private String avatar;
    private String username;
    private String code;
}
