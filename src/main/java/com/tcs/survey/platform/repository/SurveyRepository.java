package com.tcs.survey.platform.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.survey.platform.model.entity.RegisterDtoEntity;

import java.util.List;

@Transactional
@Repository
public interface SurveyRepository extends CrudRepository<RegisterDtoEntity, Long> {

    @Query("select l from RegisterDtoEntity l where l.refno = :refno")
    RegisterDtoEntity findByreferenceNum(
            @Param(value = "refno") final String refno);

    @Modifying
    @Query("delete from RegisterDtoEntity u where  u.refno = :deleterecordref")
    int deleteUsersByRefNumber(@Param(value = "deleterecordref") final String deleterecordref);

    /*@Query("select count(usertype) as pga from RegisterDtoEntity u where upper(usertype) like '%PATIENT%'")
    String patientcount();

    @Query("select count(usertype) as pga from RegisterDtoEntity u where upper(usertype) like '%CARER%'")
    String carercount();

    @Query("select count(usertype) as pga from RegisterDtoEntity u where upper(usertype) like '%LOVED ONE%'")
    String lovedcount();*/
/*
    @Query("select usertype, count(usertype),monthname(registrationdate),year(registrationdate),registrationdate from RegisterDtoEntity u where usertype in('patient') and registrationdate is not null group by monthname(registrationdate),year(registrationdate) order by cast(registrationdate as date) asc ")
    public List patientbarcount();

    @Query("select usertype, count(usertype),monthname(registrationdate),year(registrationdate),registrationdate from RegisterDtoEntity u where usertype='carer' and registrationdate is not null group by monthname(registrationdate),year(registrationdate) order by cast(registrationdate as date) asc ")
    public List carerbarcount();

    @Query("select usertype, count(usertype),monthname(registrationdate),year(registrationdate),registrationdate from RegisterDtoEntity u where usertype like '%loved one%' and registrationdate is not null group by monthname(registrationdate),year(registrationdate) order by cast(registrationdate as date) asc ")
    public List lovedbarcount();*/

    @Query("select usertype, count(usertype), dayname(registrationdate) from RegisterDtoEntity u where upper(usertype) like '%PATIENT%' and cast(registrationdate as date) >= (cast(sysdate() as date) - 7) group by registrationdate, usertype order by cast(registrationdate as date) desc")
    
   public List patientweeklybarcount();

   @Query("select usertype, count(usertype), dayname(registrationdate) from RegisterDtoEntity u where upper(usertype) like '%CARER%' and cast(registrationdate as date) >= (cast(sysdate() as date) - 7) group by registrationdate, usertype order by cast(registrationdate as date) desc")
  
   public List carerweeklybarcount();

   @Query("select usertype, count(usertype), dayname(registrationdate) from RegisterDtoEntity u where upper(usertype) like '%LOVED ONE%' and cast(registrationdate as date) >= (cast(sysdate() as date) - 7) group by registrationdate, usertype order by cast(registrationdate as date) desc")
   public List lovedweeklybarcount();

    @Query("select count(u) from RegisterDtoEntity u where u.refno=:refno")
    int referenceexist(@Param(value = "refno") final String refno);

    /*@Query("SELECT t FROM RegisterDtoEntity t WHERE (:referencenumber IS NULL OR t.referencenumber= :referencenumber) AND (:surgeryheld IS NULL OR t.surgeryheld= :surgeryheld) AND (:usertype IS NULL OR t.usertype= :usertype) AND (:surgerydelay IS NULL OR t.surgerydelay= :surgerydelay) AND (:countrybirth IS NULL OR t.countrybirth= :countrybirth) AND (:surveystatus IS NULL OR t.surveystatus= :surveystatus) AND (:trvlsurg IS NULL OR t.trvlsurg= :trvlsurg) AND (:state IS NULL OR t.state= :state) AND (:anxietycond IS NULL OR t.anxietycond= :anxietycond) AND  (:surgerydelaycount IS NULL OR t.surgerydelaycount= :surgerydelaycount) AND (:heartconds IS NULL OR t.heartconds= :heartconds) AND (:conditioncalld IS NULL OR t.conditioncalld= :conditioncalld) AND (:ethnicity IS NULL OR t.ethnicity= :ethnicity) AND  (:sex IS NULL OR t.sex= :sex) AND (:age1 IS NULL OR t.age BETWEEN :age1 AND :age2) AND  (:contctviaphone IS NULL OR t.contctviaphone= :contctviaphone)")
    public List<RegisterDtoEntity> findbysearchheartkid(@Param("referencenumber") String referencenumber, @Param("countrybirth") String countrybirth, @Param("surveystatus") String surveystatus, @Param("surgeryheld") String surgeryheld, @Param("surgerydelay") String surgerydelay, @Param("trvlsurg") String trvlsurg, @Param("anxietycond") String anxietycond, @Param("state") String state, @Param("surgerydelaycount") String surgerydelaycount, @Param("heartconds") String heartconds, @Param("usertype") String usertype, @Param("conditioncalld") String conditioncalld, @Param("ethnicity") String ethnicity, @Param("sex") String sex, @Param("age1") String age1, @Param("age2") String age2, @Param("contctviaphone") String contctviaphone);
    */
    
    @Query(value= "SELECT refno FROM surveyuserprofile ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public String findbylastreferencenumber();
    
   /* @Query("select id from RegisterDtoEntity t WHERE t.firstname=:firstname and t.lastname= :lastname and t.surveystatus != :status AND t.email=:email AND t.usertype= :usertype AND t.usertype = :usertype AND t.birthdate= :dob)")
    public long userrecordExist(@Param(value = "firstname") final String firstname,@Param(value = "lastname") final String lastname,@Param(value = "email") final String email,@Param(value = "usertype") final String usertype,@Param(value = "dob") final String dob,@Param(value = "status") final String status);
   
    @Query("select id from RegisterDtoEntity t WHERE t.firstname=:firstname and t.lastname= :lastname and t.surveystatus != :status AND t.email=:email AND t.usertype= :usertype AND t.usertype = :usertype AND t.birthdate= :dob AND t.carerfirstname=:carerfirstname AND t.carerlastname=:carerlastname AND t.careremail=:careremail )")
    public long userrecordCarerExist(@Param(value = "firstname") final String firstname,@Param(value = "lastname") final String lastname,@Param(value = "email") final String email,@Param(value = "usertype") final String usertype,@Param(value = "dob") final String dob,@Param(value = "status") final String status,@Param(value = "carerfirstname") final String carerfirstname,@Param(value = "carerlastname") final String carerlastname,@Param(value = "careremail") final String careremail);
   *//* @Modifying
    @Query("update u RegisterDtoEntity u where u.id=:ID")
    public RegisterDtoEntity updateexistingrecord(@Param(value = "ID") final String ID);*/

	}
