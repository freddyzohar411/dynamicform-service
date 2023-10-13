package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.entity.FormSubmissionsEntity;
import com.avensys.rts.formservice.payloadrequest.FormSubmissionsRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormSubmissionsResponseDTO;
import com.avensys.rts.formservice.repository.FormSubmissionsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormSubmissionServiceImpl implements FormSubmissionsService {

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

    private FormSubmissionsEntity formSubmissionsRequestDTOToformSubmissionEntity(FormSubmissionsRequestDTO formSubmissionsRequestDTO, Integer userId) {
        FormSubmissionsEntity formSubmissionsEntity = new FormSubmissionsEntity();
        formSubmissionsEntity.setFormId(formSubmissionsRequestDTO.getFormId());
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonSubmissionData = null;
//        try {
//            jsonSubmissionData = objectMapper.writeValueAsString(formSubmissionsRequestDTO.getSubmissionData());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("JSON:" + jsonSubmissionData);
//        formSubmissionsEntity.setSubmissionData(jsonSubmissionData);
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
}
