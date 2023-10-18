package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import com.avensys.rts.formservice.payloadrequest.FormSubmissionsRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormSubmissionsResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FormSubmissionsService {

    FormSubmissionsResponseDTO createFormSubmission(FormSubmissionsRequestDTO formSubmissionsRequestDTO);

    FormSubmissionsResponseDTO getFormSubmission(Integer id);

    List<FormSubmissionsResponseDTO> getAllFormSubmissions();

    FormSubmissionsResponseDTO updateFormSubmission(Integer id, FormSubmissionsRequestDTO formSubmissionsRequestDTO);

    void deleteFormSubmission(Integer id);

    List<FormSubmissionsEntity> getFormFieldList(String entityName);

    List<Map<String, String>> getFormFieldNameList(String entityName);

    Page<FormSubmissionsEntity> getFormSubmissionPage(Integer page, Integer size, String sortBy, String sortDirection);

    Page<FormSubmissionsEntity> getFormSubmissionPageWithSearch(Integer page, Integer size, String sortBy, String sortDirection, String searchTerm);

}
