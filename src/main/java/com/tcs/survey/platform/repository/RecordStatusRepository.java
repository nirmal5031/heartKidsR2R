package com.tcs.survey.platform.repository;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

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
	public interface RecordStatusRepository extends CrudRepository<RecordStatusEntity, Long> {

	@Query("select count(usertype) as pga from RecordStatusEntity u where upper(usertype) like '%PATIENT%'")
    String patientcount();
	
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

	   
	    @Modifying
	    @Query("update RecordStatusEntity u set u.status=:status, u.completion_level=:completion where u.refno=:refno")
	    public void updateCompletionLevelBy(@Param(value="refno") final String refno,@Param(value="status") final String status,@Param(value="completion") final String completion);
	    
    @Query("select count(usertype) as pga from RecordStatusEntity u where upper(usertype) like '%CARER%'")
    String carercount();

 /* @Query(value="select u from ", nativeQuery=true)
  List<String> distinctrefno();*/

  @Query(value="SELECT usertype, COUNT(*) FROM surveystatus GROUP BY usertype", nativeQuery=true)
  public List<Object> getRecordCount();
  
  @Query("select count(u) from RecordStatusEntity u")
  int registationcount();

  
  @Query("select u.completion_level from RecordStatusEntity u where refno=:refno")
public String checkifsectioncomplete(@Param(value="refno") final String refno);
    
}
