package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormSubmissionsRepository extends JpaRepository<FormSubmissionsEntity, Integer> {
}
