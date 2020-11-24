package sustech.edu.phantom.dboj.mapper;
import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.form.Pagination;

import java.util.List;

public interface AnnouncementMapper {
    /**
     * 分页查询
     * @param pagination 分页表单
     * @return
     */
    List<Announcement> queryAnnouncement(Pagination pagination);

    /**
     *
     * @param announcementList
     * @return
     */
    int insertAnnouncement(@Param("announcementList") List<Announcement> announcementList);

    /**
     *
     * @param announcement
     * @return
     */
    int insertOneAnnouncement(Announcement announcement);

    Integer getCount(Pagination pagination);
}
