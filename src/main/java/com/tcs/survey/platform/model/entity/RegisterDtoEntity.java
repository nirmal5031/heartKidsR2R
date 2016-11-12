package com.tcs.survey.platform.model.entity;

import org.pojomatic.annotations.AutoProperty;

import javax.persistence.*;

@Entity
@Table(name = "heartkidregistration")
@AutoProperty
public class RegisterDtoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String refno;
    @Column(name = "USERTYPE")
    private String usertype;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    private String title;
    private String sex;
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
    private String passedones;
    private String conditioncalld;
    private String heartconds;
    private String surgeryheld;
    private String surgerydelay;
    private String surgerydelaycount;
    private String trvlsurg;
    private String heartdoc;
    private String localdoctorvisit;
    private String emergdeptvisit;
    private String childtoadultdoc;
    private String anxietycond;
    private String anxietycondimpact;
    private String curntwork;
    private String worktime;
    private String disabilityben;
    private String chdlivingimpc;
    private String changeimpactchd;
    private String chdimpactwork;
     private String eductnchallng;
     private String missschooldays;
     private String miscarerchooldays;
    private String schoolgrd;
    private String formalassess;
    @Column(name = "IMPACTQOL")
    private String impactqol;
    private String condimpactschl;
    private String condimpactschooldesc;
    private String familymodel;
    private String emotimpct;
    private String relationimpact;
    private String siblingchld;
    private String moneyspentinyear;
    private String frstsurgerysel;
    private String hosptlsurgery;
    private String educsupporthosp;
    private String transpaedtoadult;
    private String ratetransition;
    private String feelsupport;
    private String heartkidsupport;
    private String socworker;
    private String pstcologist;
    private String familysuprt;
    private String dedicatedCHDnurse;
    private String doctorcountsee;
    private String traveldistdoc;
    private String aftrsurgfeel;
    private String heardheartkid;
    private String memberheartkid;
    private String supportheartkid;
    private String useheartkid;
    private String desccommentsany;
    private String status;
    private String registrationdate;
    private String updateddate;

    public RegisterDtoEntity() {

    }

    public RegisterDtoEntity(long id, String usertype, String firstname,
                             String lastname, String title, String sex, String postcode,
                             String state, String status, String conctagree,
                             String contctviaphone, String contctviaemail, String phone,
                             String email, String ethnicity, String countrybirth,
                             String language, String conditioncalld, String heartconds,
                             String surgeryHeld, String surgerydelay, String Surgerydelaycount,
                             String trvlsurg, String heartdoc, String localdoctorvisit,
                             String emergdeptvisit, String careage16, String childtoadultdoc,
                             String anxietycond, String anxietycondimpact, String curntwork,
                             String worktime, String disabilityben, String chdlivingimpc,
                             String changeimpactchd, String chdimpactwork,
                             String missschooldays, String eductnchallng, String schoolgrd,
                             String formalassess, String impactQol, String condimpactschl,
                             String condimpactschooldesc, String moneyspentinyear,
                             String frstsurgerysel, String hosptlsurgery,
                             String educsupporthosp, String transpaedtoadult,
                             String ratetransition, String feelsupport, String heartkidsupport,
                             String socworker, String pstcologist, String familysuprt,
                             String dedicatednurse, String dedicatedCHDnurse,
                             String doctorcountsee, String traveldistdoc, String aftrsurgfeel,
                             String heardheartkid, String memberheartkid,
                             String supportheartkid, String useheartkid, String desccommentsany) {

        super();
        this.id = id;
        this.usertype = usertype;
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.sex = sex;
        this.birthdate = birthdate;
        this.postcode = postcode;
        this.state = state;
        this.contctviaphone = contctviaphone;
        this.phone = phone;
        this.email = email;
        this.ethnicity = ethnicity;
        this.countrybirth = countrybirth;
        this.language = language;
        this.conditioncalld = conditioncalld;
        this.heartconds = heartconds;
        this.surgeryheld = surgeryheld;
        this.surgerydelay = surgerydelay;
        this.surgerydelaycount = surgerydelaycount;
        this.trvlsurg = trvlsurg;
        this.heartdoc = heartdoc;
        this.localdoctorvisit = localdoctorvisit;
        this.emergdeptvisit = emergdeptvisit;
        this.childtoadultdoc = childtoadultdoc;
        this.anxietycond = anxietycond;
        this.anxietycondimpact = anxietycondimpact;
        this.curntwork = curntwork;
        this.worktime = worktime;
        this.disabilityben = disabilityben;
        this.chdlivingimpc = chdlivingimpc;
        this.changeimpactchd = changeimpactchd;
        this.chdimpactwork = chdimpactwork;
        this.missschooldays = missschooldays;
        this.eductnchallng = eductnchallng;
        this.schoolgrd = schoolgrd;
        this.formalassess = formalassess;
        this.impactqol = impactqol;
        this.condimpactschl = condimpactschl;
        this.condimpactschooldesc = condimpactschooldesc;
        this.moneyspentinyear = moneyspentinyear;
        this.frstsurgerysel = frstsurgerysel;
        this.hosptlsurgery = hosptlsurgery;
        this.educsupporthosp = educsupporthosp;
        this.transpaedtoadult = transpaedtoadult;
        this.ratetransition = ratetransition;
        this.feelsupport = feelsupport;
        this.heartkidsupport = heartkidsupport;
        this.socworker = socworker;
        this.pstcologist = pstcologist;
        this.familysuprt = familysuprt;
        this.dedicatedCHDnurse = dedicatedCHDnurse;
        this.doctorcountsee = doctorcountsee;
        this.traveldistdoc = traveldistdoc;
        this.aftrsurgfeel = aftrsurgfeel;
        this.heardheartkid = heardheartkid;
        this.memberheartkid = memberheartkid;
        this.supportheartkid = supportheartkid;
        this.useheartkid = useheartkid;
        this.desccommentsany = desccommentsany;
        this.status = status;

    }

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

    public String getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(String registrationdate) {
        this.registrationdate = registrationdate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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



   

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

    public String getConditioncalld() {
        return conditioncalld;
    }

    public void setConditioncalld(String conditioncalld) {
        this.conditioncalld = conditioncalld;
    }

    public String getHeartconds() {
        return heartconds;
    }

    public void setHeartconds(String heartconds) {
        this.heartconds = heartconds;
    }

    public String getSurgerydelay() {
        return surgerydelay;
    }

    public void setSurgerydelay(String surgerydelay) {
        this.surgerydelay = surgerydelay;
    }

    public String getTrvlsurg() {
        return trvlsurg;
    }

    public void setTrvlsurg(String trvlsurg) {
        this.trvlsurg = trvlsurg;
    }

    public String getHeartdoc() {
        return heartdoc;
    }

    public void setHeartdoc(String heartdoc) {
        this.heartdoc = heartdoc;
    }

    public String getLocaldoctorvisit() {
        return localdoctorvisit;
    }

    public void setLocaldoctorvisit(String localdoctorvisit) {
        this.localdoctorvisit = localdoctorvisit;
    }

    public String getEmergdeptvisit() {
        return emergdeptvisit;
    }

    public void setEmergdeptvisit(String emergdeptvisit) {
        this.emergdeptvisit = emergdeptvisit;
    }



    public String getChildtoadultdoc() {
        return childtoadultdoc;
    }

    public void setChildtoadultdoc(String childtoadultdoc) {
        this.childtoadultdoc = childtoadultdoc;
    }

    public String getAnxietycond() {
        return anxietycond;
    }

    public void setAnxietycond(String anxietycond) {
        this.anxietycond = anxietycond;
    }

    public String getAnxietycondimpact() {
        return anxietycondimpact;
    }

    public void setAnxietycondimpact(String anxietycondimpact) {
        this.anxietycondimpact = anxietycondimpact;
    }

    public String getCurntwork() {
        return curntwork;
    }

    public void setCurntwork(String curntwork) {
        this.curntwork = curntwork;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getDisabilityben() {
        return disabilityben;
    }

    public void setDisabilityben(String disabilityben) {
        this.disabilityben = disabilityben;
    }

    public String getChdlivingimpc() {
        return chdlivingimpc;
    }

    public void setChdlivingimpc(String chdlivingimpc) {
        this.chdlivingimpc = chdlivingimpc;
    }

    public String getChangeimpactchd() {
        return changeimpactchd;
    }

    public void setChangeimpactchd(String changeimpactchd) {
        this.changeimpactchd = changeimpactchd;
    }

    public String getChdimpactwork() {
        return chdimpactwork;
    }

    public void setChdimpactwork(String chdimpactwork) {
        this.chdimpactwork = chdimpactwork;
    }

    public String getMissschooldays() {
        return missschooldays;
    }

    public void setMissschooldays(String missschooldays) {
        this.missschooldays = missschooldays;
    }

    public String getEductnchallng() {
        return eductnchallng;
    }

    public void setEductnchallng(String eductnchallng) {
        this.eductnchallng = eductnchallng;
    }

    public String getSchoolgrd() {
        return schoolgrd;
    }

    public void setSchoolgrd(String schoolgrd) {
        this.schoolgrd = schoolgrd;
    }

    public String getFormalassess() {
        return formalassess;
    }

    public void setFormalassess(String formalassess) {
        this.formalassess = formalassess;
    }

    public String getSurgeryheld() {
        return surgeryheld;
    }

    public void setSurgeryheld(String surgeryheld) {
        this.surgeryheld = surgeryheld;
    }

    public String getSurgerydelaycount() {
        return surgerydelaycount;
    }

    public void setSurgerydelaycount(String surgerydelaycount) {
        this.surgerydelaycount = surgerydelaycount;
    }

    public String getImpactqol() {
        return impactqol;
    }

    public void setImpactqol(String impactqol) {
        this.impactqol = impactqol;
    }

    public String getCondimpactschl() {
        return condimpactschl;
    }

    public void setCondimpactschl(String condimpactschl) {
        this.condimpactschl = condimpactschl;
    }

    public String getCondimpactschooldesc() {
        return condimpactschooldesc;
    }

    public void setCondimpactschooldesc(String condimpactschooldesc) {
        this.condimpactschooldesc = condimpactschooldesc;
    }

    public String getMoneyspentinyear() {
        return moneyspentinyear;
    }

    public void setMoneyspentinyear(String moneyspentinyear) {
        this.moneyspentinyear = moneyspentinyear;
    }

    public String getFrstsurgerysel() {
        return frstsurgerysel;
    }

    public void setFrstsurgerysel(String frstsurgerysel) {
        this.frstsurgerysel = frstsurgerysel;
    }

    public String getHosptlsurgery() {
        return hosptlsurgery;
    }

    public void setHosptlsurgery(String hosptlsurgery) {
        this.hosptlsurgery = hosptlsurgery;
    }

    public String getEducsupporthosp() {
        return educsupporthosp;
    }

    public void setEducsupporthosp(String educsupporthosp) {
        this.educsupporthosp = educsupporthosp;
    }

    public String getTranspaedtoadult() {
        return transpaedtoadult;
    }

    public void setTranspaedtoadult(String transpaedtoadult) {
        this.transpaedtoadult = transpaedtoadult;
    }

    public String getRatetransition() {
        return ratetransition;
    }

    public void setRatetransition(String ratetransition) {
        this.ratetransition = ratetransition;
    }

    public String getFeelsupport() {
        return feelsupport;
    }

    public void setFeelsupport(String feelsupport) {
        this.feelsupport = feelsupport;
    }

    public String getHeartkidsupport() {
        return heartkidsupport;
    }

    public void setHeartkidsupport(String heartkidsupport) {
        this.heartkidsupport = heartkidsupport;
    }

    public String getSocworker() {
        return socworker;
    }

    public void setSocworker(String socworker) {
        this.socworker = socworker;
    }

    public String getPstcologist() {
        return pstcologist;
    }

    public void setPstcologist(String pstcologist) {
        this.pstcologist = pstcologist;
    }

    public String getFamilysuprt() {
        return familysuprt;
    }

    public void setFamilysuprt(String familysuprt) {
        this.familysuprt = familysuprt;
    }

    public String getDedicatedCHDnurse() {
        return dedicatedCHDnurse;
    }

    public void setDedicatedCHDnurse(String dedicatedCHDnurse) {
        this.dedicatedCHDnurse = dedicatedCHDnurse;
    }

    public String getDoctorcountsee() {
        return doctorcountsee;
    }

    public void setDoctorcountsee(String doctorcountsee) {
        this.doctorcountsee = doctorcountsee;
    }

    public String getTraveldistdoc() {
        return traveldistdoc;
    }

    public void setTraveldistdoc(String traveldistdoc) {
        this.traveldistdoc = traveldistdoc;
    }

    public String getAftrsurgfeel() {
        return aftrsurgfeel;
    }

    public void setAftrsurgfeel(String aftrsurgfeel) {
        this.aftrsurgfeel = aftrsurgfeel;
    }

    public String getHeardheartkid() {
        return heardheartkid;
    }

    public void setHeardheartkid(String heardheartkid) {
        this.heardheartkid = heardheartkid;
    }

    public String getMemberheartkid() {
        return memberheartkid;
    }

    public void setMemberheartkid(String memberheartkid) {
        this.memberheartkid = memberheartkid;
    }

    public String getSupportheartkid() {
        return supportheartkid;
    }

    public void setSupportheartkid(String supportheartkid) {
        this.supportheartkid = supportheartkid;
    }

    public String getUseheartkid() {
        return useheartkid;
    }

    public void setUseheartkid(String useheartkid) {
        this.useheartkid = useheartkid;
    }

    public String getDesccommentsany() {
        return desccommentsany;
    }

    public void setDesccommentsany(String desccommentsany) {
        this.desccommentsany = desccommentsany;
    }

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getPassedones() {
		return passedones;
	}

	public void setPassedones(String passedones) {
		this.passedones = passedones;
	}

	public String getMiscarerchooldays() {
		return miscarerchooldays;
	}

	public void setMiscarerchooldays(String miscarerchooldays) {
		this.miscarerchooldays = miscarerchooldays;
	}

	public String getFamilymodel() {
		return familymodel;
	}

	public void setFamilymodel(String familymodel) {
		this.familymodel = familymodel;
	}

	public String getEmotimpct() {
		return emotimpct;
	}

	public void setEmotimpct(String emotimpct) {
		this.emotimpct = emotimpct;
	}

	public String getRelationimpact() {
		return relationimpact;
	}

	public void setRelationimpact(String relationimpact) {
		this.relationimpact = relationimpact;
	}

	public String getSiblingchld() {
		return siblingchld;
	}

	public void setSiblingchld(String siblingchld) {
		this.siblingchld = siblingchld;
	}


}
