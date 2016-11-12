package com.tcs.survey.platform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.survey.platform.model.entity.ProfileEntity;

@Transactional
@Repository
	public interface SurveyProfileRepository extends CrudRepository<ProfileEntity, Long> {

	 	@Query(value= "SELECT refno FROM surveyuserprofile ORDER BY ID DESC LIMIT 1", nativeQuery = true)
	    public String findbylastreferencenumber();
	 
	    @Query("select refno from ProfileEntity t WHERE t.firstname=:firstname and t.lastname= :lastname  AND t.email=:email AND t.birthdate= :dob)")
	    public String userrecordExist(@Param(value = "firstname") final String firstname,@Param(value = "lastname") final String lastname,@Param(value = "email") final String email,@Param(value = "dob") final String dob);
	   
	    /*@Query("select id from ProfileEntity t WHERE t.firstname=:firstname and t.lastname= :lastname AND t.email=:email AND t.usertype= :usertype AND t.usertype = :usertype AND t.birthdate= :dob AND t.carerfirstname=:carerfirstname AND t.carerlastname=:carerlastname AND t.careremail=:careremail )")
	    public long userrecordCarerExist(@Param(value = "firstname") final String firstname,@Param(value = "lastname") final String lastname,@Param(value = "email") final String email,@Param(value = "usertype") final String usertype,@Param(value = "dob") final String dob,@Param(value = "carerfirstname") final String carerfirstname,@Param(value = "carerlastname") final String carerlastname,@Param(value = "careremail") final String careremail);
	   */
	    
	    @Query("select u from ProfileEntity u where u.refno=:refno")
	    public ProfileEntity getProfilefromRefNo(@Param(value = "refno") final String refno);
	      
}
