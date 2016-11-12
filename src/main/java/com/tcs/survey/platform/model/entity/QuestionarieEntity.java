package com.tcs.survey.platform.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class QuestionarieEntity  implements Serializable{

	private static final long serialVersionUID = 2514719982327593095L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	@JsonProperty("QuestionEntity")
    private List<QuestionEntity> questionformat;
    
    @Column(name = "refno")
    private String refno;

    public String getRefno() {
	return refno;
}
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

public void setRefno(String refno) {
	this.refno = refno;
}
public List<QuestionEntity> getQuestionformat() {
	return questionformat;
}
public void setQuestionformat(List<QuestionEntity> questionformat) {
	this.questionformat = questionformat;
}



	
}
