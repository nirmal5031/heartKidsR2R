package com.tcs.survey.platform.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.pojomatic.annotations.AutoProperty;

@Entity
@Table(name = "surveyuserprofile")
@AutoProperty
public class ProfileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String refno;
	private String firstname;
	private String lastname;
	private String title;
	private String gender;
	private String birthdate;
	private String age;
	private String postcode;
	private String state;
	private String contctviaphone;
	private String phone;
	private String email;
	private String ethnicity;
	private String countrybirth;
	private String language;
	private String carerfirstname;
	private String carerlastname;
	private String carerbirthdate;
	private String carerphone;
	private String careremail;
	private String createdBy;
	private String updatedOn;
	private String updatedBy;



	@OneToOne
	@JoinColumn(name = "refno", referencedColumnName = "refno", insertable = false, updatable = false)
	private RecordStatusEntity recordStatus;

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContctviaphone() {
		return contctviaphone;
	}

	public void setContctviaphone(String contctviaphone) {
		this.contctviaphone = contctviaphone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getCountrybirth() {
		return countrybirth;
	}

	public void setCountrybirth(String countrybirth) {
		this.countrybirth = countrybirth;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCarerfirstname() {
		return carerfirstname;
	}

	public void setCarerfirstname(String carerfirstname) {
		this.carerfirstname = carerfirstname;
	}

	public String getCarerlastname() {
		return carerlastname;
	}

	public void setCarerlastname(String carerlastname) {
		this.carerlastname = carerlastname;
	}

	public String getCarerbirthdate() {
		return carerbirthdate;
	}

	public void setCarerbirthdate(String carerbirthdate) {
		this.carerbirthdate = carerbirthdate;
	}

	public String getCarerphone() {
		return carerphone;
	}

	public void setCarerphone(String carerphone) {
		this.carerphone = carerphone;
	}

	public String getCareremail() {
		return careremail;
	}

	public void setCareremail(String careremail) {
		this.careremail = careremail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RecordStatusEntity getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatusEntity recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "ProfileEntity [id=" + id + ", refno=" + refno + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", title=" + title + ", gender=" + gender + ", birthdate=" + birthdate + ", age=" + age
				+ ", postcode=" + postcode + ", state=" + state + ", contctviaphone=" + contctviaphone + ", phone="
				+ phone + ", email=" + email + ", ethnicity=" + ethnicity + ", countrybirth=" + countrybirth
				+ ", language=" + language + ", carerfirstname=" + carerfirstname + ", carerlastname=" + carerlastname
				+ ", carerbirthdate=" + carerbirthdate + ", carerphone=" + carerphone + ", careremail=" + careremail
				+ ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy
				+ ", recordStatus=" + recordStatus + "]";
	}

	
	

}
