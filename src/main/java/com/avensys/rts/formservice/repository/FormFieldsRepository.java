package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormFieldsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormFieldsRepository extends JpaRepository<FormFieldsEntity, Integer> {
}
