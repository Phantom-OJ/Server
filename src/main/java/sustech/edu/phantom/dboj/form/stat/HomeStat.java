package sustech.edu.phantom.dboj.form.stat;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Home statistics")
public class HomeStat {
    private Date date;
    private Integer count;
}
