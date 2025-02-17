package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.entity.FormsEntity;
import com.avensys.rts.formservice.payloadrequest.FormRequestDTO;
import com.avensys.rts.formservice.payloadresponse.*;

import java.util.List;
import java.util.Map;

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

    FormListingResponseDTO getFormListingPage(Integer page, Integer size, String sortBy, String sortDirection);

    FormListingResponseDTO getFormListingPageWithSearch(Integer page, Integer pageSize, String sortBy, String sortDirection, String searchTerm);

    List<String> getFormCategories();

    FormTemplateNamesListResponseDTO getFormsTemplateNamesByCategory(String formCategory);

    Map<String, Integer> getFormTemplateNamesIdMap(List<String> formTemplateNames);
}
