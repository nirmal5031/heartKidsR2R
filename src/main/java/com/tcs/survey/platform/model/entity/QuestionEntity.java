package com.tcs.survey.platform.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.pojomatic.annotations.AutoProperty;


@Entity
@Table(name = "surveyrecords")
@AutoProperty
public class QuestionEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
		private String refno;
	private String question_id;
	private String section_id;
	private String answer;
	private String createdBy;
	private String updatedBy;
	private String updatedOn;
    private String reportfieldtitle;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRefno() {
		return refno;
	}
	public void setRefno(String refno) {
		this.refno = refno;
	}
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public String getSection_id() {
		return section_id;
	}
	public void setSection_id(String section_id) {
		this.section_id = section_id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getReportfieldtitle() {
		return reportfieldtitle;
	}
	public void setReportfieldtitle(String reportfieldtitle) {
		this.reportfieldtitle = reportfieldtitle;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
    
 
	
}
