package com.avensys.rts.formservice.repository;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomFormSubmissionsRepositoryImpl implements CustomFormSubmissionsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<FormSubmissionsEntity> findAllByFieldsAndEntityTypeAndOrderByJsonKey(List<String> fields, String entityType, String searchTerm, String sortByJsonKey, Pageable pageable) {

        // Build the WHERE clause dynamically based on the fields list
        StringBuilder whereClause = new StringBuilder(" WHERE entity_type = :entityType ");
        for (String field : fields) {
            if (field.contains(".")) {  // assuming field is in the format "jsonColumn.jsonKey"
                String[] parts = field.split("\\.");
                whereClause.append(String.format(" OR (submission_data->>'%s') ILIKE :searchTerm ", parts[1]));
            } else {
                whereClause.append(String.format(" OR %s ILIKE :searchTerm ", field));
            }
        }

        // Build the complete query string
        String queryString = String.format(
                "SELECT * FROM form_submissions %s ORDER BY (submission_data->>:sortByJsonKey)::text ASC NULLS LAST",
                whereClause.toString());

        // Create and execute the query
        Query query = entityManager.createNativeQuery(queryString, FormSubmissionsEntity.class);
        query.setParameter("entityType", entityType);
        query.setParameter("searchTerm", "%" + searchTerm + "%");  // assuming you want a substring match
        query.setParameter("sortByJsonKey", sortByJsonKey);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Get the result list
        List<FormSubmissionsEntity> resultList = query.getResultList();

        // Build the count query string
        String countQueryString = String.format(
                "SELECT COUNT(*) FROM form_submissions %s", whereClause.toString());

        // Create and execute the count query
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        countQuery.setParameter("entityType", entityType);
        countQuery.setParameter("searchTerm", "%" + searchTerm + "%");  // assuming you want a substring match
        Long countResult = ((Number) countQuery.getSingleResult()).longValue();

        // Create and return a Page object
        return new PageImpl<>(resultList, pageable, countResult);
    }

    @Override
    public Page<FormSubmissionsEntity> findAllByEntityTypeAndOrderBy(Integer userId, String entityType, Pageable pageable) {
        String sortBy = pageable.getSort().get().findFirst().get().getProperty();
        // Determine if sortBy is a regular column or a JSONB column
        String orderByClause = pageable.getSort().isSorted() ? pageable.getSort().get().findFirst().get().getProperty() : "id";
        if (sortBy.contains(".")) {  // assuming sortBy is in the format "jsonColumn.jsonKey"
            String[] parts = sortBy.split("\\.");
            String jsonColumnName = parts[0];
            String jsonKey = parts[1];
            orderByClause = String.format("(%s->>'%s')", jsonColumnName, jsonKey);
        }

        // Extract sort direction from pageable
        String sortDirection = pageable.getSort().isSorted() ? pageable.getSort().get().findFirst().get().getDirection().name() : "ASC";

        // Build the complete query string
        String queryString = String.format(
                "SELECT * FROM form_submissions WHERE user_id = :userId AND entity_type = :entityType ORDER BY %s %s NULLS LAST",
                orderByClause, sortDirection);

        // Create and execute the query
        Query query = entityManager.createNativeQuery(queryString, FormSubmissionsEntity.class);
        query.setParameter("userId", userId);
        query.setParameter("entityType", entityType);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Get the result list
        List<FormSubmissionsEntity> resultList = query.getResultList();

        // Build the count query string
        String countQueryString = "SELECT COUNT(*) FROM form_submissions WHERE user_id = :userId AND entity_type = :entityType";

        // Create and execute the count query
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        countQuery.setParameter("userId", userId);
        countQuery.setParameter("entityType", entityType);
        Long countResult = ((Number) countQuery.getSingleResult()).longValue();

        // Create and return a Page object
        return new PageImpl<>(resultList, pageable, countResult);
    }

    @Override
    public Page<FormSubmissionsEntity> findAllByEntityTypeAndOrderByAndSearch(Integer userId, String entityType, Pageable pageable, List<String> searchFields, String searchTerm) {

        // Determine if sortBy is a regular column or a JSONB column
        String sortBy = pageable.getSort().isSorted() ? pageable.getSort().get().findFirst().get().getProperty() : "id";
        String orderByClause;
        if (sortBy.contains(".")) {  // assuming sortBy is in the format "jsonColumn.jsonKey"
            String[] parts = sortBy.split("\\.");
            String jsonColumnName = parts[0];
            String jsonKey = parts[1];
            orderByClause = String.format("(%s->>'%s')", jsonColumnName, jsonKey);
        } else {
            orderByClause = sortBy;
        }

        // Extract sort direction from pageable
        String sortDirection = pageable.getSort().isSorted() ? pageable.getSort().get().findFirst().get().getDirection().name() : "ASC";

        // Build the dynamic search conditions based on searchFields
        StringBuilder searchConditions = new StringBuilder();
        for (int i = 0; i < searchFields.size(); i++) {
            String field = searchFields.get(i);
            if (field.contains(".")) {  // assuming field is in the format "jsonColumn.jsonKey"
                String[] parts = field.split("\\.");
                String jsonColumnName = parts[0];
                String jsonKey = parts[1];
                searchConditions.append(String.format(" OR (%s->>'%s') ILIKE :searchTerm ", jsonColumnName, jsonKey));
            } else {
                searchConditions.append(String.format(" OR CAST(%s AS TEXT) ILIKE :searchTerm ", field));
//                searchConditions.append(String.format(" OR %s ILIKE :searchTerm ", field));
            }
        }

        // Remove the leading " OR " from the searchConditions
        if (searchConditions.length() > 0) {
            searchConditions.delete(0, 4);
        }

        // Build the complete query string
        String queryString = String.format(
                "SELECT * FROM form_submissions WHERE user_id = :userId AND entity_type = :entityType AND (%s) ORDER BY %s %s NULLS LAST",
                searchConditions.toString(),
                orderByClause,
                sortDirection);

        // Create and execute the query
        Query query = entityManager.createNativeQuery(queryString, FormSubmissionsEntity.class);
        query.setParameter("userId", userId);
        query.setParameter("entityType", entityType);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // Get the result list
        List<FormSubmissionsEntity> resultList = query.getResultList();

        // Build the count query string
        String countQueryString = String.format(
                "SELECT COUNT(*) FROM form_submissions WHERE user_id = :userId AND entity_type = :entityType AND (%s)",
                searchConditions.toString());

        // Create and execute the count query
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        countQuery.setParameter("userId", userId);
        countQuery.setParameter("entityType", entityType);
        countQuery.setParameter("searchTerm", "%" + searchTerm + "%");
        Long countResult = ((Number) countQuery.getSingleResult()).longValue();

        // Create and return a Page object
        return new PageImpl<>(resultList, pageable, countResult);
    }
}