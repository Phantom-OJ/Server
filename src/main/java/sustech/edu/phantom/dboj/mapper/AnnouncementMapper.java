package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Announcement;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;

import java.util.List;

public interface AnnouncementMapper {
    /**
     * 分页查询
     *
     * @param pagination 分页表单
     * @return 公告列表
     */
    List<Announcement> queryAnnouncement(Pagination pagination);

    /**
     * 插入一条公告
     *
     * @param announcement 上传公告的对象
     * @return 插入的记录
     */
    int insertOneAnnouncement(UploadAnnouncementForm announcement);

    /**
     * 得到数量
     *
     * @param pagination 分页表单
     * @return 数量
     */
    Integer getCount(Pagination pagination);

    /**
     * 修改公告
     *
     * @param param1 修改公告的表单
     * @param id     公告 id
     * @return 是否成功
     */
    boolean modifyAnnouncement(@Param("param1") UploadAnnouncementForm param1, @Param("id") Integer id);

    /**
     * 删除一个公告
     *
     * @param id 公告id
     * @return 是否成功
     */
    Integer deleteAnnouncement(Integer id);
}
