package com.dali.didor.model;

public class AlarmItem {

	private int id;
	private long type;
	private long userId;
	private long creatorId;
	private String title;
	private String content;
	private String attachment;
	private AlarmRepeatType repeatType;
	private Datetime startTime;
	private Datetime endTime;
	private Datetime createdTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public AlarmRepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(AlarmRepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public Datetime getStartTime() {
		return startTime;
	}

	public void setStartTime(Datetime startTime) {
		this.startTime = startTime;
	}

	public Datetime getEndTime() {
		return endTime;
	}

	public void setEndTime(Datetime endTime) {
		this.endTime = endTime;
	}

	public Datetime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Datetime createdTime) {
		this.createdTime = createdTime;
	}

}
