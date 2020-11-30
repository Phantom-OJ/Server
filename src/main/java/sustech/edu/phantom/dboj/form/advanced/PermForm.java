package sustech.edu.phantom.dboj.form.advanced;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/26 20:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermForm {
    private Integer id;
    private String permission;
    private Integer mode; // 0 为修改 1 为增加 2 为删除
}
