package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * author: Koh He Xiang
 * This is the repository class for the account table in the database
 */
public interface FormsRepository extends JpaRepository<FormsEntity, Integer> {

    @Query(value = "SELECT f from forms f where f.formType = 'base'")
    List<FormsEntity> getBaseForm();

    Optional<FormsEntity> findByFormName(String formName);

}