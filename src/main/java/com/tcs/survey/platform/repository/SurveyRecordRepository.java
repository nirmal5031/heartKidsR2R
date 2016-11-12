package com.tcs.survey.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.survey.platform.model.entity.ProfileEntity;
import com.tcs.survey.platform.model.entity.RecordStatusEntity;

@Transactional
@Repository
	public interface SurveyRecordRepository extends CrudRepository<RecordStatusEntity, Long> {

	@Query("select count(usertype) as pga from RecordStatusEntity u where upper(usertype) like '%PATIENT%'")
    String patientcount();

    @Query("select count(usertype) as pga from RecordStatusEntity u where upper(usertype) like '%CARER%'")
    String carercount();

    @Query("select count(usertype) as pga from RecordStatusEntity u where upper(usertype) like '%LOVED ONE%'")
    String lovedcount();
    
    @Query("select usertype, count(usertype),monthname(registrationdate),year(registrationdate),registrationdate from RecordStatusEntity u where usertype in('patient') and registrationdate is not null group by monthname(registrationdate),year(registrationdate) order by cast(registrationdate as date) asc ")
    public List patientbarcount();
   
    @Query("select usertype, count(usertype),monthname(registrationdate),year(registrationdate),registrationdate from RecordStatusEntity u where usertype='carer' and registrationdate is not null group by monthname(registrationdate),year(registrationdate) order by cast(registrationdate as date) asc ")
    public List carerbarcount();

    @Query("select usertype, count(usertype),monthname(registrationdate),year(registrationdate),registrationdate from RecordStatusEntity u where usertype like '%loved one%' and registrationdate is not null group by monthname(registrationdate),year(registrationdate) order by cast(registrationdate as date) asc ")
    public List lovedbarcount();
    
    @Query("select usertype, count(usertype), dayname(registrationdate) from RecordStatusEntity u where upper(usertype) like '%PATIENT%' and cast(registrationdate as date) >= (cast(sysdate() as date) - 7) group by registrationdate, usertype order by cast(registrationdate as date) desc")
    
   public List patientweeklybarcount();

   @Query("select usertype, count(usertype), dayname(registrationdate) from RecordStatusEntity u where upper(usertype) like '%CARER%' and cast(registrationdate as date) >= (cast(sysdate() as date) - 7) group by registrationdate, usertype order by cast(registrationdate as date) desc")
  
   public List carerweeklybarcount();

   @Query("select usertype, count(usertype), dayname(registrationdate) from RecordStatusEntity u where upper(usertype) like '%LOVED ONE%' and cast(registrationdate as date) >= (cast(sysdate() as date) - 7) group by registrationdate, usertype order by cast(registrationdate as date) desc")
   public List lovedweeklybarcount();

   @Query(value="select distinct(refno) from hk_tbl_record_status where completion_level is not null", nativeQuery = true)
   public List<String> distinctrefno();
   
   @Modifying
   @Query("update RecordStatusEntity u set u.completion_level = :completion_level, u.status = :status where u.refno=:refno")
  public void updateCompletionLevelBy(@Param(value = "refno") final String refno,@Param(value = "completion_level") final String completionLevel,@Param(value = "status") final String status);
    
   @Query("select count(u) from RecordStatusEntity u")
   int registationcount();
   
   @Query("select u from RecordStatusEntity u where u.refno=:refno")
   public RecordStatusEntity getRecordsfromRefNo(@Param(value = "refno") final String refno);
   
   
  /* @Query("SELECT t FROM RecordStatusEntity t WHERE (:referencenumber IS NULL OR t.referencenumber= :referencenumber) AND (:usertype IS NULL OR t.usertype= :usertype) AND (:status IS NULL OR t.status= :status)")
   public RecordStatusEntity findbysearchStatusRecord(@Param("referencenumber") String referencenumber, @Param("status") String status,@Param("usertype") String usertype);
  */ 
    

}
