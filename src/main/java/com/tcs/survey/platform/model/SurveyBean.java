package com.tcs.survey.platform.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.QuestionEntity;

public class SurveyBean {

	private Long id;
	private ProfileEntity profile;
	private  List<QuestionEntity> questionaire;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProfileEntity getProfile() {
		return profile;
	}
	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}
	public  List<QuestionEntity> getQuestionaire() {
		return questionaire;
	}
	public void setQuestionaire( List<QuestionEntity> questionaire) {
		this.questionaire = questionaire;
	}
	
  

 
    

	
	
	
}
