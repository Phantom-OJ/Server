package sustech.edu.phantom.dboj.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

/**
 * @author Lori
 */
@ApiModel(description = "分页过滤器")
public class Pagination {
    @ApiModelProperty(value = "start index")
    private int start;
    @ApiModelProperty(value = "end index")
    private int end;
    private transient int limit;
    private transient int offset;
    @ApiModelProperty(value = "customized filters")
    private HashMap<String, Object> filter;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setParameters() {
        this.limit = getEnd() - getStart() + 1;
        this.offset = getStart() - 1;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public HashMap<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(HashMap<String, Object> filter) {
        this.filter = filter;
    }
}
