package com.avensys.rts.formservice.controller;

import com.avensys.rts.formservice.constant.MessageConstants;
import com.avensys.rts.formservice.payloadrequest.FormRequestDTO;
import com.avensys.rts.formservice.payloadrequest.FormSubmissionsRequestDTO;
import com.avensys.rts.formservice.payloadresponse.FormSubmissionsResponseDTO;
import com.avensys.rts.formservice.service.FormServiceImpl;
import com.avensys.rts.formservice.service.FormSubmissionServiceImpl;
import com.avensys.rts.formservice.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FormSubmissionsController {
    private final Logger log = LoggerFactory.getLogger(FormSubmissionsController.class);

    private final FormSubmissionServiceImpl formSubmissionService;
    private final MessageSource messageSource;

    public FormSubmissionsController(FormSubmissionServiceImpl formSubmissionService, MessageSource messageSource) {
        this.formSubmissionService = formSubmissionService;
        this.messageSource = messageSource;
    }

    @PostMapping("/form-submissions")
    public ResponseEntity<Object> addFormSubmission(@RequestBody FormSubmissionsRequestDTO formSubmissionsRequestDTO) {
        log.info("Form Submission create: Controller");
        FormSubmissionsResponseDTO formSubmissionsResponseDTO = formSubmissionService.createFormSubmission(formSubmissionsRequestDTO);
        return ResponseUtil.generateSuccessResponse(formSubmissionsResponseDTO, HttpStatus.CREATED, messageSource.getMessage(MessageConstants.MESSAGE_CREATED, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/form-submissions/{formSubmissionId}")
    public ResponseEntity<Object> getFormSubmission(@PathVariable int formSubmissionId) {
        log.info("Form Submission get: Controller");
        FormSubmissionsResponseDTO formSubmissionsResponseDTO = formSubmissionService.getFormSubmission(formSubmissionId);
        return ResponseUtil.generateSuccessResponse(formSubmissionsResponseDTO, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/form-submissions")
    public ResponseEntity<Object> getAllFormSubmissions() {
        log.info("Form Submission get: Controller");
        return ResponseUtil.generateSuccessResponse(formSubmissionService.getAllFormSubmissions(), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping("/form-submissions/{formSubmissionId}")
    public ResponseEntity<Object> deleteFormSubmission(@PathVariable int formSubmissionId) {
        log.info("Form Submission delete: Controller");
        formSubmissionService.deleteFormSubmission(formSubmissionId);
        return ResponseUtil.generateSuccessResponse(null, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @PutMapping("/form-submissions/{formSubmissionId}")
    public ResponseEntity<Object> updateFormSubmission(@PathVariable int formSubmissionId, @RequestBody FormSubmissionsRequestDTO formSubmissionsRequestDTO) {
        log.info("Form Submission update: Controller");
        FormSubmissionsResponseDTO formSubmissionsResponseDTO = formSubmissionService.updateFormSubmission(formSubmissionId, formSubmissionsRequestDTO);
        return ResponseUtil.generateSuccessResponse(formSubmissionsResponseDTO, HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/form-submissions/entity/{entityName}")
    public ResponseEntity<Object> getFormFieldList(@PathVariable String entityName) {
        log.info("Form Submission get: Controller");
        return ResponseUtil.generateSuccessResponse(formSubmissionService.getFormFieldList(entityName), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    @GetMapping("/form-submissions/entity/{entityName}/names")
    public ResponseEntity<Object> getFormFieldNameList(@PathVariable String entityName) {
        log.info("Form Submission get: Controller");
        return ResponseUtil.generateSuccessResponse(formSubmissionService.getFormFieldNameList(entityName), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    // Sorting and pagination
    @GetMapping("/form-submissions-page")
    public ResponseEntity<Object> getAllAccounts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                 @RequestParam(required = false) String sortBy,
                                                 @RequestParam(required = false) String sortDirection
    ) {
        log.info("List form submissionns: Controller");
        return ResponseUtil.generateSuccessResponse(formSubmissionService.getFormSubmissionPage(page, pageSize, sortBy,sortDirection), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

    // Sorting and pagination with search
    @GetMapping("/form-submissions-page-search")
    public ResponseEntity<Object> getAllAccountsWithSearch(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                 @RequestParam(required = false) String sortBy,
                                                 @RequestParam(required = false) String sortDirection,
                                                 @RequestParam(required = false) String searchTerm
                                                    ) {
        log.info("List form submissions: Controller");
        System.out.println("Page: " + page);
        System.out.println("PageSize: " + pageSize);
        if (searchTerm == null || searchTerm.isEmpty()) {
            return ResponseUtil.generateSuccessResponse(formSubmissionService.getFormSubmissionPage(page, pageSize, sortBy,sortDirection), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
        }
        return ResponseUtil.generateSuccessResponse(formSubmissionService.getFormSubmissionPageWithSearch(page, pageSize, sortBy,sortDirection, searchTerm), HttpStatus.OK, messageSource.getMessage(MessageConstants.MESSAGE_SUCCESS, null, LocaleContextHolder.getLocale()));
    }

}
