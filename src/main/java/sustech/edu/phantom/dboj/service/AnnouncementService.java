package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.entity.vo.EntityVO;
import sustech.edu.phantom.dboj.form.home.Pagination;
import sustech.edu.phantom.dboj.mapper.AnnouncementMapper;

import java.util.List;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AnnouncementService {
    @Autowired
    AnnouncementMapper announcementMapper;

    public List<Announcement> getAnnouncementList(Pagination pagination) {
        return announcementMapper.queryAnnouncement(pagination);
    }

    public EntityVO<Announcement> announcementEntityVO(Pagination pagination) {
        pagination.setParameters();
        return new EntityVO<>(announcementMapper.queryAnnouncement(pagination), announcementMapper.getCount(pagination));
    }
}