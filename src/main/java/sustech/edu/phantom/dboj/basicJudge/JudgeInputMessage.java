package sustech.edu.phantom.dboj.basicJudge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeInputMessage {
    ArrayList<JudgeInput> judgeInputs;
    Integer codeId;
    Integer problemId;
    Integer userId;
    String dialect;
}
