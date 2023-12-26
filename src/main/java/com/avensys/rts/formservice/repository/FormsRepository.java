package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * author: Koh He Xiang
 * This is the repository class for the account table in the database
 */
public interface FormsRepository extends JpaRepository<FormsEntity, Integer>, JpaSpecificationExecutor<FormsEntity> {

    @Query(value = "SELECT f from forms f where f.formType = 'base'")
    List<FormsEntity> getBaseForm();

    Optional<FormsEntity> findByFormName(String formName);

    @Query(value = "SELECT f from forms f")
    Page<FormsEntity> findAllByPaginationAndSort(Pageable pageable);

    Page<FormsEntity> findAll(Specification<FormsEntity> specification, Pageable pageable);

    // Get a list of Form categories
    @Query(value = "SELECT DISTINCT f.formCategory FROM forms f")
    List<String> getFormCategories();

    // Get list of forms by form category
    @Query(value = "SELECT f FROM forms f WHERE f.formCategory = ?1")
    List<FormsEntity> getFormsByFormCategory(String formCategory);

}