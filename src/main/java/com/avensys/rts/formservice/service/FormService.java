package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.entity.FormsEntity;
import com.avensys.rts.formservice.payloadrequest.FormRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormListResponse;
import com.avensys.rts.formservice.payloadresponse.FormResponseDTO;

import java.util.List;

/**
 * @author Koh He Xiang
 * This interface is used to declare methods for AccountService
 */
public interface FormService {

    /**
     * This method is used to create account
     * @param
     * @return
     */
    FormResponseDTO createForm(FormRequestDTO formRequest);

    /**
     * This method is used to get account
     * @param
     * @return
     */
    FormResponseDTO getForm(int formId);

    List<FormListResponse> getAllForms();

    FormResponseDTO updateForm(FormRequestDTO formRequest, int formId);

    void deleteForm(int formId);

    List<FormListResponse> getBaseForms();

    FormResponseDTO getFormByName(String formName);

}
