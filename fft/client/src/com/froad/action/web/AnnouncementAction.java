package com.froad.action.web;

import com.froad.action.support.AnnouncementActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.announcement.Announcement;

/** 
 * @author FQ 
 * @date 2013-3-4 上午11:34:57
 * @version 1.0
 * 
 */
public class AnnouncementAction extends BaseActionSupport {
	
	private AnnouncementActionSupport announcementActionSupport;
	
	private Announcement announcement;//公告
	
	/**
	 * 查看 公告
	 * @return
	 */
	public String info(){
		
		announcement=announcementActionSupport.getAnnouncementById(Integer.parseInt(id));
		
		return "info";
	}
	
	public AnnouncementActionSupport getAnnouncementActionSupport() {
		return announcementActionSupport;
	}

	public void setAnnouncementActionSupport(
			AnnouncementActionSupport announcementActionSupport) {
		this.announcementActionSupport = announcementActionSupport;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}
}
