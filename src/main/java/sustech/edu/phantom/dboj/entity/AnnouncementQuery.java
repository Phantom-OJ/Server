package sustech.edu.phantom.dboj.entity;

public class AnnouncementQuery {
    private int start;
    private int end;
    private transient int limit;
    private transient int offset;

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
}
