package sustech.edu.phantom.dboj.mapper;
import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Announcement;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;

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
    int insertOneAnnouncement(UploadAnnouncementForm announcement);

    Integer getCount(Pagination pagination);
}
