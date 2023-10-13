package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.SectionsEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionsRepository extends JpaRepository<SectionsEntity, Integer> {
    List<SectionsEntity> findByFormsEntityId(Integer formId, Sort sort);
}
