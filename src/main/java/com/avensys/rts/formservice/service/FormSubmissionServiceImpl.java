package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.APIClient.UserAPIClient;
import com.avensys.rts.formservice.customresponse.HttpResponse;
import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import com.avensys.rts.formservice.payloadrequest.FormSubmissionFieldUniqueCheckRequestDTO;
import com.avensys.rts.formservice.payloadrequest.FormSubmissionsRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormSubmissionsResponseDTO;
import com.avensys.rts.formservice.payloadresponse.UserResponseDTO;
import com.avensys.rts.formservice.repository.FormSubmissionsRepository;
import com.avensys.rts.formservice.util.JwtUtil;
import com.avensys.rts.formservice.util.MappingUtil;
import com.avensys.rts.formservice.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FormSubmissionServiceImpl implements FormSubmissionsService {

    @Autowired
    private UserAPIClient userAPIClient;

    private final FormSubmissionsRepository formSubmissionsRepository;

    public FormSubmissionServiceImpl(FormSubmissionsRepository formSubmissionsRepository) {
        this.formSubmissionsRepository = formSubmissionsRepository;
    }

    @Override
    public FormSubmissionsResponseDTO createFormSubmission(FormSubmissionsRequestDTO formSubmissionsRequestDTO) {

        System.out.println(formSubmissionsRequestDTO.getSubmissionData());
        FormSubmissionsEntity formSubmissionsEntity = formSubmissionsRequestDTOToformSubmissionEntity(formSubmissionsRequestDTO, 1);
        FormSubmissionsEntity savedFormSubmissionEntity = formSubmissionsRepository.save(formSubmissionsEntity);
        return formSubmissionsEntityToFormSubmissionsResponseDTO(savedFormSubmissionEntity);
    }

    @Override
    public FormSubmissionsResponseDTO getFormSubmission(Integer id) {
        FormSubmissionsEntity foundFormSubmissionEntity = formSubmissionsRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Form Submission not found")
        );
        return formSubmissionsEntityToFormSubmissionsResponseDTO(foundFormSubmissionEntity);
    }

    @Override
    public List<FormSubmissionsResponseDTO> getAllFormSubmissions() {
        List<FormSubmissionsEntity> formSubmissionsEntities = formSubmissionsRepository.findAll();
        return formSubmissionsEntities.stream().map(this::formSubmissionsEntityToFormSubmissionsResponseDTO).toList();
    }

    @Override
    public FormSubmissionsResponseDTO updateFormSubmission(Integer id, FormSubmissionsRequestDTO formSubmissionsRequestDTO) {
        FormSubmissionsEntity formSubmissionsEntity = formSubmissionsRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Form Submission not found")
        );
        FormSubmissionsEntity updatedFormSubmissionEntity = updateFormSubmissionEntityToFormSubmissionsResponseDTO(formSubmissionsEntity, formSubmissionsRequestDTO);
        return formSubmissionsEntityToFormSubmissionsResponseDTO(updatedFormSubmissionEntity);
    }

    @Override
    public void deleteFormSubmission(Integer id) {
        formSubmissionsRepository.deleteById(id);
    }

    @Override
    public List<FormSubmissionsEntity> getFormFieldList(String entityName) {
        List<FormSubmissionsEntity> formSubmission = formSubmissionsRepository.findAllByUserIdAndAndEntityType(getUserId(), entityName);
        return formSubmission;
    }

    @Override
    public List<Map<String, String>> getFormFieldNameList(String entityName) {
        List<FormSubmissionsEntity> formSubmissions = formSubmissionsRepository.findAllByUserIdAndAndEntityType(getUserId(), entityName);
        if (formSubmissions.isEmpty()) {
            return null;
        }
//        Set<String> keySet = new HashSet<>();
        Map<String, String> keyMap = new HashMap<>();
        for (FormSubmissionsEntity formSubmissionsEntity : formSubmissions) {
            Iterator<String> fieldNames = formSubmissionsEntity.getSubmissionData().fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                keyMap.put(StringUtil.convertCamelCaseToTitleCase2(fieldName), "submission_data." + fieldName );
//                keySet.add(fieldName);
            }
        }
        List<Map<String, String>> fieldOptions = new ArrayList<>();
        // Loop Through map
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
            // Creat a list of map with label and value
            Map<String, String> map = new HashMap<>();
            map.put("label", entry.getKey());
            map.put("value", entry.getValue());
            fieldOptions.add(map);
        }

        return fieldOptions;
    }

    @Override
    public List<Map<String, String>> getFormFieldNameList2(String entityName) {
        List<FormSubmissionsEntity> formSubmissions = formSubmissionsRepository.findAllByUserIdAndAndEntityType(getUserId(), entityName);
        if (formSubmissions.isEmpty()) {
            return null;
        }
        Map<String, String> keyMap = new HashMap<>();
        for (FormSubmissionsEntity formSubmissionsEntity : formSubmissions) {
            Iterator<String> fieldNames = formSubmissionsEntity.getSubmissionData().fieldNames();
            while (fieldNames.hasNext()) {
                // if undefined then skip
                String fieldName = fieldNames.next();
                if (fieldName.equals("undefined")) {
                    continue;
                }
                keyMap.put(StringUtil.convertCamelCaseToTitleCase2(fieldName), fieldName );
            }
        }
        List<Map<String, String>> fieldOptions = new ArrayList<>();
        // Loop Through map
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            // Creat a list of map with label and value
            Map<String, String> map = new HashMap<>();
            map.put("label", entry.getKey());
            map.put("value", entry.getValue());
            fieldOptions.add(map);
        }

        return fieldOptions;
    }

    @Override
    public List<Map<String, String>> getFormFieldNameList3(String entityName) {
        List<FormSubmissionsEntity> formSubmissions = formSubmissionsRepository.findAllByEntityType(entityName);
        if (formSubmissions.isEmpty()) {
            return null;
        }
        Map<String, String> keyMap = new HashMap<>();
        for (FormSubmissionsEntity formSubmissionsEntity : formSubmissions) {
            Iterator<String> fieldNames = formSubmissionsEntity.getSubmissionData().fieldNames();
            while (fieldNames.hasNext()) {
                // if undefined then skip
                String fieldName = fieldNames.next();
                if (fieldName.equals("undefined")) {
                    continue;
                }
                keyMap.put(StringUtil.convertCamelCaseToTitleCase2(fieldName), fieldName );
            }
        }
        List<Map<String, String>> fieldOptions = new ArrayList<>();
        // Loop Through map
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            // Creat a list of map with label and value
            Map<String, String> map = new HashMap<>();
            map.put("label", entry.getKey());
            map.put("value", entry.getValue());
            fieldOptions.add(map);
        }

        return fieldOptions;
    }

    @Override
    public Page<FormSubmissionsEntity> getFormSubmissionPage(Integer page, Integer size, String sortBy, String sortDirection) {
        // Get sort direction
        Sort.Direction direction = Sort.DEFAULT_DIRECTION;
        if (sortDirection != null) {
            direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        }
        if (sortBy == null) {
            sortBy = "id";
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return formSubmissionsRepository.findAllByEntityTypeAndOrderBy(
                getUserId(),
                "account_account",
                pageRequest
        );
    }

    @Override
    public Page<FormSubmissionsEntity> getFormSubmissionPageWithSearch(Integer page, Integer size, String sortBy, String sortDirection, String searchTerm) {
        System.out.println("Starting getFormSubmissionPageWithSearch");
        Sort.Direction direction = Sort.DEFAULT_DIRECTION;
        if (sortDirection != null) {
            direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        }
        if (sortBy == null) {
            sortBy = "id";
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return formSubmissionsRepository.findAllByEntityTypeAndOrderByAndSearch(
                getUserId(),
                "account_account",
                pageRequest,
                List.of("id","submission_data.accountName", "submission_data.accountStatus", "submission_data.accountType"),
                searchTerm
        );
    }

    @Override
    public Boolean checkIfFieldValueUnique(
            FormSubmissionFieldUniqueCheckRequestDTO formSubmissionFieldUniqueCheckRequestDTO) {
       return formSubmissionsRepository.existsByFormIdAndSubmissionDataField(
                formSubmissionFieldUniqueCheckRequestDTO.getFormId(),
                formSubmissionFieldUniqueCheckRequestDTO.getFieldName(),
                formSubmissionFieldUniqueCheckRequestDTO.getFieldValue()
        );
    }

    //    public Specification<FormSubmissionsEntity> sortByField(String fieldName, Sort.Direction direction) {
//        return (root, query, criteriaBuilder) -> {
//            Path<?> fieldPath;
//            if (fieldName.contains(".")) {
//                String[] nestedProps = fieldName.split("\\.");
//                Join<Object, Object> join = root.join(nestedProps[0], JoinType.LEFT);
//                for (int i = 1; i < nestedProps.length - 1; i++) {
//                    join = join.join(nestedProps[i], JoinType.LEFT);
//                }
//                fieldPath = join.get(nestedProps[nestedProps.length - 1]);
//            } else {
//                fieldPath = root.get(fieldName);
//            }
//            return (Predicate) (direction == Sort.Direction.ASC ? criteriaBuilder.asc(fieldPath) : criteriaBuilder.desc(fieldPath));
//        };
//    }
public  Specification<FormSubmissionsEntity> sortByField(String sortBy, Sort.Direction direction) {
    return (root, query, criteriaBuilder) -> {
        Order order;
        System.out.println("sortBy: " + sortBy);
        if (sortBy.contains(".")) {
            // Assuming sortBy is in the format "jsonColumn.jsonKey"
            String[] parts = sortBy.split("\\.");
            String jsonColumn = parts[0];
            String jsonKey = parts[1];
            System.out.println("jsonColumn: " + jsonColumn);
            System.out.println("jsonKey: " + jsonKey);
            Expression<String> jsonKeyValueExpression = criteriaBuilder.function(
                    "->>",
                    String.class,
                    root.get(jsonColumn),
                    criteriaBuilder.literal(jsonKey)
            );
            order = (direction == Sort.Direction.ASC) ?
                    criteriaBuilder.asc(jsonKeyValueExpression) :
                    criteriaBuilder.desc(jsonKeyValueExpression);
        } else {
            Path<Object> fieldPath = root.get(sortBy);
            order = (direction == Sort.Direction.ASC) ?
                    criteriaBuilder.asc(fieldPath) :
                    criteriaBuilder.desc(fieldPath);
        }
        query.orderBy(order);
        return criteriaBuilder.conjunction();  // returns a predicate that always evaluates to true
    };
}
    public Specification<FormSubmissionsEntity> filterByEntityType(String entityType) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("entityType"), entityType);
        };
    }


private FormSubmissionsEntity formSubmissionsRequestDTOToformSubmissionEntity(FormSubmissionsRequestDTO formSubmissionsRequestDTO, Integer userId) {
        FormSubmissionsEntity formSubmissionsEntity = new FormSubmissionsEntity();
        formSubmissionsEntity.setFormId(formSubmissionsRequestDTO.getFormId());
        formSubmissionsEntity.setSubmissionData(formSubmissionsRequestDTO.getSubmissionData());
        formSubmissionsEntity.setEntityId(formSubmissionsRequestDTO.getEntityId());
        formSubmissionsEntity.setEntityType(formSubmissionsRequestDTO.getEntityType());
        formSubmissionsEntity.setUserId(userId);
        return formSubmissionsEntity;
    }

    private FormSubmissionsEntity updateFormSubmissionEntityToFormSubmissionsResponseDTO(FormSubmissionsEntity formSubmissionsEntity, FormSubmissionsRequestDTO updateformSubmissionsRequestDTO) {
        formSubmissionsEntity.setFormId(updateformSubmissionsRequestDTO.getFormId());
        formSubmissionsEntity.setSubmissionData(updateformSubmissionsRequestDTO.getSubmissionData());
        formSubmissionsEntity.setEntityId(updateformSubmissionsRequestDTO.getEntityId());
        formSubmissionsEntity.setEntityType(updateformSubmissionsRequestDTO.getEntityType());
        return formSubmissionsRepository.save(formSubmissionsEntity);
    }
    private FormSubmissionsResponseDTO formSubmissionsEntityToFormSubmissionsResponseDTO(FormSubmissionsEntity formSubmissionsEntity) {
        FormSubmissionsResponseDTO formSubmissionsResponseDTO = new FormSubmissionsResponseDTO();
        formSubmissionsResponseDTO.setId(formSubmissionsEntity.getId());
        formSubmissionsResponseDTO.setFormId(formSubmissionsEntity.getFormId());
        formSubmissionsResponseDTO.setSubmissionData(formSubmissionsEntity.getSubmissionData());
        formSubmissionsResponseDTO.setEntityId(formSubmissionsEntity.getEntityId());
        formSubmissionsResponseDTO.setEntityType(formSubmissionsEntity.getEntityType());
        return formSubmissionsResponseDTO;
    }

    private Integer getUserId() {
        String email = JwtUtil.getEmailFromContext();
        HttpResponse userResponse = userAPIClient.getUserByEmail(email);
        UserResponseDTO userData = MappingUtil.mapClientBodyToClass(userResponse.getData(), UserResponseDTO.class);
        return userData.getId();
    }
}
