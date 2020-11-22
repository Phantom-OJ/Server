package sustech.edu.phantom.dboj.form.stat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemStatSet {
    List<ProblemStat> resultSet;
    List<ProblemStat> dialectSet;
}
