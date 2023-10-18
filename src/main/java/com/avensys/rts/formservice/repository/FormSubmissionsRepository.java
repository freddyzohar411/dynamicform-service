package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FormSubmissionsRepository extends JpaRepository<FormSubmissionsEntity, Integer>, CustomFormSubmissionsRepository {

    List<FormSubmissionsEntity> findAllByUserIdAndAndEntityType(Integer userId, String entityType);

}
