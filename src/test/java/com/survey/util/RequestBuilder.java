package com.survey.util;

import com.tcs.survey.platform.model.entity.RegisterDtoEntity;

public class RequestBuilder {

    private RegisterDtoEntity registerDtoEntity;

    private long id;
    private String refno;
    private String usertype;
    private String firstname;
    private String lastname;
    private String title;
    private String birthdate;
    private String postcode;
    private String state;
    private String conctagree;
    private String contctviaphone;
    private String contctviaemail;
    private String phone;
    private String email;
    private String ethnicity;
    private String countrybirth;
    private String language;
    private String carerfirstname;
    private String carerlastname;
    private String carertitle;
    private String carerbirthdate;
    private String carerphone;
    private String careremail;
    private String conditioncalld;
    private String heartconds;
    private String surgeryheld;
    private String surgerydelay;
    private String surgerydelaycount;
    private String trvlsurg;
    private String heartdoc;
    private String localdoctorvisit;
    private String emergdeptvisit;
    private String careage16;
    private String childtoadultdoc;
    private String anxietycond;
    private String anxietycondimpact;
    private String curntwork;
    private String worktime;
    private String disabilityben;
    private String chdlivingimpc;
    private String changeimpactchd;
    private String chdimpactwork;
    private String missschooldays;
    private String eductnchallng;
    private String schoolgrd;
    private String formalassess;
    private String impactqol;
    private String condimpactschl;
    private String condimpactschooldesc;
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
    private String dedicatednurse;
    private String dedicatedCHDnurse;
    private String doctorcountsee;
    private String traveldistdoc;
    private String aftrsurgfeel;
    private String heardheartkid;
    private String memberheartkid;
    private String supportheartkid;
    private String useheartkid;
    private String desccommentsany;
    private String surveystatus;
    private String registrationdate;

    public RequestBuilder() {

    }

    public RequestBuilder(long id, String refno, String usertype, String firstname, String lastname, String title,
                          String postcode, String state, String surveystatus, String conctagree,
                          String contctviaphone, String contctviaemail, String phone, String email,
                          String ethnicity, String countrybirth, String language, String conditioncalld,
                          String heartconds, String surgeryheld, String surgerydelay, String Surgerydelaycount,
                          String trvlsurg, String heartdoc, String localdoctorvisit, String emergdeptvisit,
                          String careage16, String childtoadultdoc, String anxietycond, String anxietycondimpact,
                          String curntwork, String worktime, String disabilityben, String chdlivingimpc, String changeimpactchd,
                          String chdimpactwork, String missschooldays, String eductnchallng, String schoolgrd,
                          String formalassess, String impactQol, String condimpactschl, String condimpactschooldesc,
                          String moneyspentinyear, String frstsurgerysel, String hosptlsurgery, String educsupporthosp,
                          String transpaedtoadult, String ratetransition, String feelsupport, String heartkidsupport,
                          String socworker, String pstcologist, String familysuprt, String dedicatednurse,
                          String dedicatedCHDnurse, String doctorcountsee, String traveldistdoc, String aftrsurgfeel,
                          String heardheartkid, String memberheartkid, String supportheartkid, String useheartkid,
                          String desccommentsany) {
        this.id = id;
        this.refno = refno;
        this.usertype = usertype;
        this.firstname = firstname;
        this.lastname = lastname;
        this.title = title;
        this.birthdate = birthdate;
        this.postcode = postcode;
        this.state = state;
        this.conctagree = conctagree;
        this.contctviaphone = contctviaphone;
        this.contctviaemail = contctviaemail;
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
        this.careage16 = careage16;
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
        this.dedicatednurse = dedicatednurse;
        this.dedicatedCHDnurse = dedicatedCHDnurse;
        this.doctorcountsee = doctorcountsee;
        this.traveldistdoc = traveldistdoc;
        this.aftrsurgfeel = aftrsurgfeel;
        this.heardheartkid = heardheartkid;
        this.memberheartkid = memberheartkid;
        this.supportheartkid = supportheartkid;
        this.useheartkid = useheartkid;
        this.desccommentsany = desccommentsany;
        this.surveystatus = surveystatus;
    }

    public RegisterDtoEntity build() {
        return new RegisterDtoEntity(id, usertype, firstname, lastname, title,
                postcode, state, surveystatus, conctagree,
                contctviaphone, contctviaemail, phone, email,
                ethnicity, countrybirth, language, conditioncalld,
                heartconds, surgeryheld, surgerydelay, surgerydelaycount,
                trvlsurg, heartdoc, localdoctorvisit, emergdeptvisit,
                careage16, childtoadultdoc, anxietycond, anxietycondimpact,
                curntwork, worktime, disabilityben, chdlivingimpc, changeimpactchd,
                chdimpactwork, missschooldays, eductnchallng, schoolgrd,
                formalassess, impactqol, condimpactschl, condimpactschooldesc,
                moneyspentinyear, frstsurgerysel, hosptlsurgery, educsupporthosp,
                transpaedtoadult, ratetransition, feelsupport, heartkidsupport,
                socworker, pstcologist, familysuprt, dedicatednurse,
                dedicatedCHDnurse, doctorcountsee, traveldistdoc, aftrsurgfeel,
                heardheartkid, memberheartkid, supportheartkid, useheartkid,
                desccommentsany, aftrsurgfeel);
    }

    public RegisterDtoEntity defaultValues() {
        RegisterDtoEntity registerDtoEntity = new RegisterDtoEntity(1, "Patient", "Test", "Test", "Mr.",
                "2020", "NSW", "Completed", "Yes", "Yes", "Yes", "0423564098", "test@qantas.com.au",
                null, "Australia", "English", null, "Yes", "Yes", "Yes", "55",
                "Test", "Yes", "No", "No", "Yes", null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null);
        registerDtoEntity.setRefno("ABCD1234");
        registerDtoEntity.setAge("5-6");
        return registerDtoEntity;
    }

    public RegisterDtoEntity updateValues() {
        RegisterDtoEntity registerDtoEntity = new RegisterDtoEntity(1, "Patient", "Test", "Test", "Mr.",
                "2020", "NSW", "Completed", "Yes", "Yes", "Yes", "0423564098", "test@qantas.com.au",
                null, "Australia", "English", null, "Yes", "Yes", "Yes", "5",
                "Test", "Yes", "No", "No", "Yes", null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null);
        registerDtoEntity.setRefno("ABCD1234");
        registerDtoEntity.setUpdateddate("31/12/2015");
        return registerDtoEntity;
    }
}
