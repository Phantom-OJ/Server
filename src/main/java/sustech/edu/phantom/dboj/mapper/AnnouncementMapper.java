package sustech.edu.phantom.dboj.mapper;
import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface AnnouncementMapper {
    List<Announcement> queryAnnouncement(Pagination pagination);

    int insertAnnouncement(@Param("announcementList") List<Announcement> announcementList);
}
