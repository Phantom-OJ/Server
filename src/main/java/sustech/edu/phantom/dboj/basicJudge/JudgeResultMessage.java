package sustech.edu.phantom.dboj.basicJudge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeResultMessage {
    ArrayList<JudgeResult> judgeResults;
    Integer recordId;
    Integer codeId;
    Integer problemId;
    Integer userId;
    Integer judgeMode;
}
