package sustech.edu.phantom.dboj.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/11/24 22:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse<T> {
    private transient final static String SUCCESS = "success";
    private transient final static String FAIL = "fail";
    private String msg;
    private T data;
}
