package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.entity.Announcement;
import sustech.edu.phantom.dboj.form.Pagination;
import sustech.edu.phantom.dboj.mapper.AnnouncementMapper;

import java.util.List;

/**
 * @author Lori
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AnnouncementService {
    @Autowired
    AnnouncementMapper announcementMapper;

    public List<Announcement> getAnnouncementList(Pagination pagination) {
        return announcementMapper.queryAnnouncement(pagination);
    }
}
