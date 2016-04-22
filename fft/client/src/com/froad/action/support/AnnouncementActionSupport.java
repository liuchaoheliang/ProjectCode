package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.announcement.Announcement;
import com.froad.client.announcement.AnnouncementService;

/**
 * @author FQ
 * @version 1.0
 * @date 2013-3-4 上午11:04:28
 */
public class AnnouncementActionSupport {

    private static Logger logger = Logger.getLogger(AnnouncementActionSupport.class);

    private AnnouncementService announcementService;

    /**
     * 根据ID 查找公告
     *
     * @param id
     * @return
     */
    public Announcement getAnnouncementById(Integer id) {
        return announcementService.getAnnouncementById(id);
    }

    /**
     * 取等级最高的公告
     *
     * @return
     */
    public Announcement getAnnouncementOrderByImportant() {
        Announcement announcement = null;
        List<Announcement> announcementList = announcementService.getAnnouncementOrderByImportantList();
        if (announcementList != null && !announcementList.isEmpty()) {
            announcement = announcementList.get(0);
        }
        return announcement;
    }

    /**
     * 取等级最高的三条公告
     *
     * @return
     */
    public List<Announcement> getAnnouncementOrderListByImportant() {
        List<Announcement> result = new ArrayList<Announcement>();
        List<Announcement> announcementList = announcementService.getAnnouncementOrderByImportantList();
        if (announcementList != null && !announcementList.isEmpty()) {
            if (3 >= announcementList.size()) {
                result = announcementList;
            } else {
                result = announcementList.subList(0, 3);
            }
        }
        return result;
    }


    public AnnouncementService getAnnouncementService() {
        return announcementService;
    }

    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }


}
