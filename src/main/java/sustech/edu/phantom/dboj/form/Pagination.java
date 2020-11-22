package sustech.edu.phantom.dboj.form;

import java.util.HashMap;

public class Pagination {
    private int start;
    private int end;
    private transient int limit;
    private transient int offset;
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
