package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormSubmissionsRepository
		extends JpaRepository<FormSubmissionsEntity, Integer>, CustomFormSubmissionsRepository {


	List<FormSubmissionsEntity> findAllByUserIdAndAndEntityType(Integer userId, String entityType);

	List<FormSubmissionsEntity> findAllByEntityType(String entityType);

	@Query(value = "SELECT EXISTS (" + "SELECT 1 FROM form_submissions " + "WHERE form_id = :formId "
			+ "AND submission_data ->> :fieldName = :fieldValue)", nativeQuery = true)
	boolean existsByFormIdAndSubmissionDataField(Long formId, String fieldName, String fieldValue);
}
