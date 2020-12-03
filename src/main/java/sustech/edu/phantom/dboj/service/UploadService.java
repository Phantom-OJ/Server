package sustech.edu.phantom.dboj.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sustech.edu.phantom.dboj.form.upload.UploadAnnouncementForm;
import sustech.edu.phantom.dboj.mapper.AnnouncementMapper;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/4 02:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UploadService {

    @Autowired
    AnnouncementMapper announcementMapper;

    public boolean saveAnnouncement(UploadAnnouncementForm form) {
        int flag = announcementMapper.insertOneAnnouncement(form);
        return flag != 0;
    }
}
