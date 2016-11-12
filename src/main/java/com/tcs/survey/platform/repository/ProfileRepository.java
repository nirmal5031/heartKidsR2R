package com.tcs.survey.platform.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.survey.platform.model.entity.ProfileEntity;


@Transactional
@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long> {

	@Query(value = "SELECT refno FROM surveyuserprofile ORDER BY ID DESC LIMIT 1", nativeQuery = true)
	public String findbylastreferencenumber();

	@Query("select refno from ProfileEntity t WHERE t.firstname=:firstname and t.lastname= :lastname  AND t.email=:email AND t.birthdate= :dob)")
	public String userrecordExist(@Param(value = "firstname") final String firstname,
			@Param(value = "lastname") final String lastname, @Param(value = "email") final String email,
			@Param(value = "dob") final String dob);

	@Query("select u from ProfileEntity u where u.refno=:refno")
	public ProfileEntity getProfilefromRefNo(@Param(value = "refno") final String refno);
	
	@Query("select u from ProfileEntity u")
	public List<ProfileEntity> getAllProfiles() ;
	
	@Query("select u from ProfileEntity u where u.refno=:refno")
	public ProfileEntity getAllProfilesDetails(@Param(value = "refno") String refno) ;

	@Query(value = "select * from surveyuserprofile  where state=:state", nativeQuery = true)
	public List<String> findBySearch(@Param(value = "state") String state);

	@Query(value = "select refno from surveyuserprofile  where refno=:refno", nativeQuery = true)
	public List<String> getrecordsBySearch(@Param("refno") String refno);

	@Query("SELECT refno FROM ProfileEntity t WHERE (:referencenumber IS NULL OR t.refno= :referencenumber) AND (:countrybirth IS NULL OR t.countrybirth= :countrybirth) AND (:state IS NULL OR t.state= :state) AND (:ethnicity IS NULL OR t.ethnicity= :ethnicity) AND  (:sex IS NULL OR t.gender= :sex)")
	public List<String> getrecordsByProfileSearch(@Param("referencenumber") String referencenumber,
			@Param("countrybirth") String countrybirth, @Param("state") String state,
			@Param("ethnicity") String ethnicity, @Param("sex") String sex);
	
	@Query("select u from ProfileEntity u where (:usertype IS NULL OR u.recordStatus.usertype= :usertype) AND (:status IS NULL OR u.recordStatus.status= :status) AND (:refno IS NULL OR u.refno= :refno) AND (:countrybirth IS NULL OR u.countrybirth= :countrybirth) AND (:state IS NULL OR u.state= :state) AND (:ethnicity IS NULL OR u.ethnicity= :ethnicity) AND (:sex IS NULL OR u.gender= :sex) AND (:age1 IS NULL OR u.age BETWEEN :age1 AND :age2))")
	public List<ProfileEntity> getRecordStatus(@Param(value = "usertype") String usertype, @Param(value = "status") String status, @Param(value = "refno") String refno, @Param(value = "sex") String sex, @Param(value = "state") String state, @Param(value = "ethnicity") String ethnicity, @Param(value = "countrybirth") String countrybirth, @Param(value = "age1") String age1, @Param(value = "age2") String age2) ;

	
//	@Query("select u from ProfileEntity u where (:refno IS NULL OR u.refno= :refno)")
//	public List<ProfileEntity> getRecordStatus(@Param(value = "refno") String refno) ;
	
	 @Modifying
	    @Query("delete from ProfileEntity u where  u.refno = :deleterecordref")
	    int deleteProfileByRefNumber(@Param(value = "deleterecordref") final String deleterecordref);

	 @Query("select count(u) from ProfileEntity u  where refno=:refno")
	    int refNumberExist(@Param(value = "refno") final String refno);
	 
}