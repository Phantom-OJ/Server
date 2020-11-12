package sustech.edu.phantom.dboj.form;

public class Pagination {
    private int start;
    private int end;
    private transient int limit;
    private transient int offset;
    private String filter;

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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
