package com.notbook.things;

import java.io.Serializable;

public class Thing implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String type;//类型
	private String time;//提醒时间
	private String time_uique;//提醒时间
	private String selecttime;//选择的日期
	private String alarmTime;//提前时间
	private String subject;//主题
	private String word;//记事
	private String picUrl;//图片
	private String priority;//优先
	private String warnUrl;//铃音
	private String voiceUrl;//录音	
	private String tixing_id;//本地提醒
	private String timeuique ;//时间戳
	private String date ;
	public Thing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Thing(Integer id, String type, String time, String time_uique,
			String selecttime, String alarmTime, String subject, String word,
			String picUrl, String priority, String warnUrl, String voiceUrl,
			String tixing_id, String timeuique, String date) {
		super();
		this.id = id;
		this.type = type;
		this.time = time;
		this.time_uique = time_uique;
		this.selecttime = selecttime;
		this.alarmTime = alarmTime;
		this.subject = subject;
		this.word = word;
		this.picUrl = picUrl;
		this.priority = priority;
		this.warnUrl = warnUrl;
		this.voiceUrl = voiceUrl;
		this.tixing_id = tixing_id;
		this.timeuique = timeuique;
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTime_uique() {
		return time_uique;
	}
	public void setTime_uique(String time_uique) {
		this.time_uique = time_uique;
	}
	public String getSelecttime() {
		return selecttime;
	}
	public void setSelecttime(String selecttime) {
		this.selecttime = selecttime;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getWarnUrl() {
		return warnUrl;
	}
	public void setWarnUrl(String warnUrl) {
		this.warnUrl = warnUrl;
	}
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	public String getTixing_id() {
		return tixing_id;
	}
	public void setTixing_id(String tixing_id) {
		this.tixing_id = tixing_id;
	}
	public String getTimeuique() {
		return timeuique;
	}
	public void setTimeuique(String timeuique) {
		this.timeuique = timeuique;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Thing [id=" + id + ", type=" + type + ", time=" + time
				+ ", time_uique=" + time_uique + ", selecttime=" + selecttime
				+ ", alarmTime=" + alarmTime + ", subject=" + subject
				+ ", word=" + word + ", picUrl=" + picUrl + ", priority="
				+ priority + ", warnUrl=" + warnUrl + ", voiceUrl=" + voiceUrl
				+ ", tixing_id=" + tixing_id + ", timeuique=" + timeuique
				+ ", date=" + date + "]";
	}
	
	
	

	
	
	
	
	
	
}
