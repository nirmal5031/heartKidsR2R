package com.tcs.survey.platform.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.tcs.survey.platform.model.entity.QuestionEntity;

@Transactional
@Repository
public interface SurveyQueRepository extends CrudRepository<QuestionEntity, Long> {

	@Query(value = "select * from surveyrecords  where refno=:refno", nativeQuery = true)
	public List<QuestionEntity> getSurveyRespByRefNo(@Param(value = "refno") final String refno);

	@Query(value = "select * from surveyrecords  where refno in (select distinct(refno) from surveyrecords where completion_level is not null)", nativeQuery = true)
	public List<QuestionEntity> getSurveyRespByuniqueID();

	@Modifying
	@Query("update QuestionEntity u set u.answer =:answer, u.updatedOn=:updatedOn, u.updatedBy=:updatedBy where u.refno=:refno AND u.question_id = :question_id")
	public void updateQuestionRecords(@Param(value = "answer") final String answer,
			@Param(value = "question_id") final String question_id, @Param(value = "refno") final String refno,
			@Param(value = "updatedOn") final String updatedOn, @Param(value = "updatedBy") final String updatedBy);

	@Query("select u from QuestionEntity u")
	public List<QuestionEntity> getAllsurvey();

	@Query("select u from QuestionEntity u where (:conditioncalld IS NULL OR (u.question_id= 'conditioncalld' AND u.answer= :conditioncalld )) AND (:heartconds IS NULL OR (u.question_id= 'heartconds' AND u.answer= :heartconds)) AND (:surgerydelaycount IS NULL OR (u.question_id= 'surgerydelaycount' AND u.answer= :surgerydelaycount)) AND (:surgeryheld IS NULL OR (u.question_id= 'surgeryheld' AND u.answer= :surgeryheld)) AND (:surgerydelay IS NULL OR (u.question_id= 'surgerydelay' AND u.answer= :surgerydelay)) AND (:trvlsurg IS NULL OR (u.question_id= 'trvlsurg' AND u.answer= :trvlsurg)) AND (:anxietycond IS NULL OR (u.question_id= 'anxietycond' AND u.answer= :anxietycond))  ")
	public List<QuestionEntity> getAllsurvey(@Param(value = "conditioncalld") String conditioncalld,
			@Param(value = "heartconds") String heartconds,@Param(value = "surgerydelaycount") String surgerydelaycount,@Param(value = "surgeryheld") String surgeryheld, @Param(value = "surgerydelay") String surgerydelay, @Param(value = "trvlsurg") String trvlsurg, @Param(value = "anxietycond") String anxietycond 
 );
@Modifying
	@Query("delete from QuestionEntity u where  u.refno = :deleterecordref")
	int deleteQuestionByRefNumber(@Param(value = "deleterecordref") final String deleterecordref);
}
