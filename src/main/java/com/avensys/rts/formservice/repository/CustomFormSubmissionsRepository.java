package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomFormSubmissionsRepository {
    Page<FormSubmissionsEntity> findAllByFieldsAndEntityTypeAndOrderByJsonKey(
            List<String> fields, String entityType, String searchTerm, String sortByJsonKey, Pageable pageable);

     Page<FormSubmissionsEntity> findAllByEntityTypeAndOrderBy(Integer userId, String entityType, Pageable pageable);

        Page<FormSubmissionsEntity> findAllByEntityTypeAndOrderByAndSearch(Integer userId, String entityType, Pageable pageable, List<String>searchFields, String searchTerm);
}
