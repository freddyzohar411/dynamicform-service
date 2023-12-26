package com.avensys.rts.formservice.controller;

import com.avensys.rts.formservice.constant.MessageConstants;
import com.avensys.rts.formservice.payloadrequest.FormListingRequestDTO;
import com.avensys.rts.formservice.payloadrequest.FormRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormResponseDTO;
import com.avensys.rts.formservice.payloadresponse.FormTemplateNamesListResponseDTO;
import com.avensys.rts.formservice.payloadresponse.FormTemplateNamesResponseDTO;
import com.avensys.rts.formservice.service.FormServiceImpl;
import com.avensys.rts.formservice.util.ResponseUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author Koh He Xiang
 * This class is used to handle the API calls for the account service.
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FormController {

    private final Logger log = LoggerFactory.getLogger(FormController.class);

    private final FormServiceImpl formService;
    private final MessageSource messageSource;

    public FormController(FormServiceImpl formService, MessageSource messageSource) {
        this.formService = formService;
        this.messageSource = messageSource;
    }

    /**
     * This method is used to create a form
     * @return
     */
    @PostMapping("/forms")
    public ResponseEntity<Object> addForm(@RequestBody FormRequestDTO formRequest) {
        log.info("Form create: Controller");
        formService.createForm(formRequest);
        return ResponseUtil.generateSuccessResponse(null, HttpStatus.CREATED, messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/forms/{formId}")
    public ResponseEntity<Object> getForm(@PathVariable int formId) {
        log.info("Form get: Controller");
        FormResponseDTO formResponseDTO = formService.getForm(formId);
        return ResponseUtil.generateSuccessResponse(formResponseDTO, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/forms")
    public ResponseEntity<Object> getAllForms() {
        log.info("Form get: Controller");
        return ResponseUtil.generateSuccessResponse(formService.getAllForms(), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @PutMapping("/forms/{formId}")
    public ResponseEntity<Object> updateForm(@RequestBody FormRequestDTO formRequest, @PathVariable int formId) {
        log.info("Form update: Controller");
        System.out.println(formRequest);
        FormResponseDTO formResponseDTO = formService.updateForm(formRequest, formId);
        return ResponseUtil.generateSuccessResponse(formResponseDTO, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping("/forms/{formId}")
    public ResponseEntity<Object> deleteForm(@PathVariable int formId) {
        log.info("Form delete: Controller");
        formService.deleteForm(formId);
        return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/forms/base")
    public ResponseEntity<Object> getBaseForms() {
        log.info("Form get: Controller");
        return ResponseUtil.generateSuccessResponse(formService.getBaseForms(), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/forms/name/{formName}")
    public ResponseEntity<Object> getFormByName(@PathVariable String formName) {
        log.info("Form get: Controller");
        FormResponseDTO formResponseDTO = formService.getFormByName(formName);
        return ResponseUtil.generateSuccessResponse(formResponseDTO, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @PostMapping("/forms/listing")
    public ResponseEntity<Object> getFormListing(@RequestBody FormListingRequestDTO formListingRequestDTO)  {
        log.info("Account get all fields: Controller");
        Integer page = formListingRequestDTO.getPage();
        Integer pageSize = formListingRequestDTO.getPageSize();
        String sortBy = formListingRequestDTO.getSortBy();
        String sortDirection = formListingRequestDTO.getSortDirection();
        String searchTerm = formListingRequestDTO.getSearchTerm();
        System.out.println(("Test 1"));
        System.out.println("Page: " + page);
        System.out.println("PageSize: " + pageSize);
        System.out.println("SortBy: " + sortBy);
        System.out.println("SortDirection: " + sortDirection);
        System.out.println("SearchTerm: " + searchTerm);
        if (searchTerm == null || searchTerm.isEmpty()) {
            System.out.println(("Test 2"));
            return ResponseUtil.generateSuccessResponse(formService.getFormListingPage(page, pageSize, sortBy, sortDirection), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
        }
        return ResponseUtil.generateSuccessResponse(formService.getFormListingPageWithSearch(
                page, pageSize, sortBy, sortDirection, searchTerm), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/forms/categories")
    public ResponseEntity<Object> getFormCategories() {
        log.info("Form get: Controller");
        List<String> formCategories = formService.getFormCategories();
        return ResponseUtil.generateSuccessResponse(formCategories, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/forms/categories/{formCategory}")
    public ResponseEntity<Object> getFormByCategory(@PathVariable String formCategory) {
        System.out.println("Form Cat:" + formCategory.isEmpty() + " " + formCategory);
        log.info("Form get: Controller");
        FormTemplateNamesListResponseDTO formResponseDTOList = formService.getFormsTemplateNamesByCategory(formCategory);
        return ResponseUtil.generateSuccessResponse(formResponseDTOList, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

}
