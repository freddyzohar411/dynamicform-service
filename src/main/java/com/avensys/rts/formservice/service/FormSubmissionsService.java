package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.payloadrequest.FormSubmissionsRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormSubmissionsResponseDTO;

import java.util.List;

public interface FormSubmissionsService {

    FormSubmissionsResponseDTO createFormSubmission(FormSubmissionsRequestDTO formSubmissionsRequestDTO);

    FormSubmissionsResponseDTO getFormSubmission(Integer id);

    List<FormSubmissionsResponseDTO> getAllFormSubmissions();

    FormSubmissionsResponseDTO updateFormSubmission(Integer id, FormSubmissionsRequestDTO formSubmissionsRequestDTO);

    void deleteFormSubmission(Integer id);



}
