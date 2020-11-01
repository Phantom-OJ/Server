package sustech.edu.phantom.dboj.mapper;
import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.AnnouncementQuery;

import java.util.List;

public interface AnnouncementMapper {
    List<Announcement> queryAnnouncement(AnnouncementQuery announcementQuery);

    int insertAnnouncement(@Param("announcementList") List<Announcement> announcementList);
}
