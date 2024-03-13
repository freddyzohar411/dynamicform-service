package com.avensys.rts.formservice.service;

import com.avensys.rts.formservice.entity.FormFieldsEntity;
import com.avensys.rts.formservice.entity.FormsEntity;
import com.avensys.rts.formservice.entity.SectionsEntity;
import com.avensys.rts.formservice.payload.DroppableZone;
import com.avensys.rts.formservice.payload.FormFieldDTO;
import com.avensys.rts.formservice.payloadrequest.FormRequestDTO;
import com.avensys.rts.formservice.payload.FormSchemaDTO;
import com.avensys.rts.formservice.payloadresponse.*;
import com.avensys.rts.formservice.repository.FormFieldsRepository;
import com.avensys.rts.formservice.repository.FormsRepository;
import com.avensys.rts.formservice.repository.SectionsRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Koh He Xiang This class is used to implement the AccountService
 *         interface and perform CRUD operations
 */
@Service
public class FormServiceImpl implements FormService {
	private final Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

	@Autowired
	private final FormsRepository formsRepository;
	private final FormFieldsRepository formFieldsRepository;
	private final SectionsRepository sectionsRepository;

	public FormServiceImpl(FormsRepository formsRepository, FormFieldsRepository formFieldsRepository,
			SectionsRepository sectionsRepository) {
		this.formsRepository = formsRepository;
		this.formFieldsRepository = formFieldsRepository;
		this.sectionsRepository = sectionsRepository;
	}

	@Override
	@Transactional
	public FormResponseDTO createForm(FormRequestDTO formRequest) {
		System.out.println("Form create: Service");
		System.out.println(formRequest);
		List<FormFieldDTO> formFieldList = formRequest.getFormFieldsList();
		List<FormSchemaDTO> formSchemaList = formRequest.getFormSchemaList();

		System.out.println("Form Field List Length: " + formFieldList.size());
		System.out.println("Form Schema List Length: " + formSchemaList.size());

		// Create form and form Id
		FormsEntity savedForm = mapFormRequestDTOToFormEntity(formRequest, 1);
		int formId = savedForm.getId();
		System.out.println("Form ID: " + formId);

		int totalSections = formSchemaList.size();
		System.out.println("Total Sections: " + totalSections);
		// Create form section -> form field
		for (int i = 0; i < totalSections; i++) {
			FormSchemaDTO formSchemaDTO = formSchemaList.get(i);
			List<DroppableZone> droppableZones = formSchemaList.get(i).getDroppableZones();
			SectionsEntity savedSection = mapSectionDTOToSectionEntity(formSchemaDTO, savedForm, i + 1);
			for (int j = 0; j < droppableZones.size(); j++) {
				List<String> fieldIds = droppableZones.get(j).getFieldIds();
				System.out.println("Form Row: " + (i + 1) + " Form Column: " + (j + 1));
				System.out.println("Field ID List Length: " + fieldIds.size());
				for (int k = 0; k < fieldIds.size(); k++) {
					String fieldId = fieldIds.get(k);
					System.out.println("Field ID: " + fieldIds.get(k));
					// Find the field in the form field list
					FormFieldDTO formFieldDTO = formFieldList.stream()
							.filter(formField -> formField.getFieldId().equals(fieldId)).findFirst().orElse(null);
					System.out.println("Form Field: " + formFieldDTO.getLabel());
					mapFormFieldDTOToFormFieldEntity(formFieldDTO, savedForm, savedSection, j + 1, k + 1);
				}
			}
		}

		// Set predefined not used into form field
		formFieldList.forEach((formField) -> {
			if (formField.getFieldType().equals("predefined") && formField.getIsUsed() == false) {
				FormFieldsEntity formFieldsEntity = new FormFieldsEntity();
				formFieldsEntity.setLabel(formField.getLabel());
				formFieldsEntity.setType(formField.getType());
				formFieldsEntity.setPlaceholder(formField.getPlaceholder());
				formFieldsEntity.setIsActive(false);
				formFieldsEntity.setMinLength(formField.getMinLength());
				formFieldsEntity.setMaxLength(formField.getMaxLength());
				formFieldsEntity.setIsRequired(formField.getRequired());
				formFieldsEntity.setFieldName(formField.getName());
				formFieldsEntity.setColumnOrder(0);
				formFieldsEntity.setColumnNumber(0);
				formFieldsEntity.setFieldId(formField.getFieldId());
				formFieldsEntity.setFieldType(formField.getFieldType());
				formFieldsEntity.setFormsEntity(savedForm);
				formFieldsEntity.setSectionsEntity(null);
				formFieldsEntity.setButtonText(formField.getButtonText());
				formFieldsEntity.setButtonType(formField.getButtonType());
				formFieldsEntity.setButtonName(formField.getButtonName());
				formFieldsEntity.setButtonClass(formField.getButtonClass());
				formFieldsEntity.setButtonLocation(formField.getButtonLocation());
				formFieldsEntity.setRequiredErrorMessage(formField.getRequiredErrorMessage());
				formFieldsEntity.setMinLengthErrorMessage(formField.getMinLengthErrorMessage());
				formFieldsEntity.setMaxLengthErrorMessage(formField.getMaxLengthErrorMessage());
				formFieldsEntity.setRegexErrorMessage(formField.getPatternValidationErrorMessage());
				formFieldsEntity.setEmailValidation(formField.getEmailValidation());
				formFieldsEntity.setEmailValidationErrorMessage(formField.getEmailValidationErrorMessage());
				formFieldsEntity.setMaxValue(formField.getMaxValue());
				formFieldsEntity.setMaxValueErrorMessage(formField.getMaxValueErrorMessage());
				formFieldsEntity.setMinValue(formField.getMinValue());
				formFieldsEntity.setMinValueErrorMessage(formField.getMinValueErrorMessage());
				formFieldsEntity.setFileTypeValidation(formField.getFileTypeValidation());
				formFieldsEntity.setFileTypeValidationErrorMessage(formField.getFileTypeValidationErrorMessage());
				formFieldsEntity.setFileSizeValidation(formField.getFileSizeValidation());
				formFieldsEntity.setFileSizeValidationErrorMessage(formField.getFileSizeValidationErrorMessage());
				formFieldsEntity.setOptions(formField.getOptions());
				formFieldsEntity.setParent(formField.getParent());
				formFieldsEntity.setVisible(formField.getVisible());
				formFieldsEntity.setUserGroup(formField.getUserGroup());
				formFieldsEntity.setTableConfig(formField.getTableConfig());
				formFieldsEntity.setTableSetting(formField.getTableSetting());
				formFieldsEntity.setCountryOptions(formField.getCountryOptions());
				formFieldsEntity.setSubName(formField.getSubName());
				formFieldsEntity.setCopyFields(formField.getCopyFields());
				formFieldsEntity.setFieldLocation(formField.getFieldLocation());
				formFieldsEntity.setFieldSize(formField.getFieldSize());
				formFieldsEntity.setWordSize(formField.getWordSize());
				formFieldsEntity.setWordText(formField.getWordText());
				// Added 15112023
				formFieldsEntity.setList(formField.getList());

				// Added 16112023
				formFieldsEntity.setConditionValidation(formField.getConditionValidation());
				formFieldsEntity.setConditionValidationErrorMessage(formField.getConditionValidationErrorMessage());

				// Added 26122023
				formFieldsEntity.setFormCategorySelect(formField.getFormCategorySelect());
				formFieldsEntity.setInformation(formField.getInformation());
				formFieldsEntity.setInformationText(formField.getInformationText());

				formFieldsRepository.save(formFieldsEntity);
			}
		});
		return null;
	}

	@Override
	public FormResponseDTO getForm(int formId) {
		FormsEntity formFound = formsRepository.findById(formId)
				.orElseThrow(() -> new EntityNotFoundException("Form with id " + formId + " not found"));
		return formsEntityToFormResponseDTO(formFound);
	}

	@Override
	public List<FormListResponse> getAllForms() {
		return formsRepository.findAll().stream().map(formsEntity -> {
			FormListResponse formListResponse = new FormListResponse();
			formListResponse.setFormId(formsEntity.getId());
			formListResponse.setFormName(formsEntity.getFormName());
			formListResponse.setFormType(formsEntity.getFormType());
			formListResponse.setEntityType(formsEntity.getEntityType());
			formListResponse.setStepperNumber(formsEntity.getStepperNumber());
			if (formsEntity.getBaseForm() != null) {
				formListResponse.setBaseFormName(formsEntity.getBaseForm().getFormName());
			} else {
				formListResponse.setBaseFormName("");
			}
			formListResponse.setUpdatedBy("Avensys");
			formListResponse.setUpdatedAt(formsEntity.getUpdatedAt());
			formListResponse.setFormCategory(formsEntity.getFormCategory());
			return formListResponse;
		}).toList();
	}

	@Override
	@Transactional
	public FormResponseDTO updateForm(FormRequestDTO formRequest, int formId) {
		System.out.println("Form update: Service");
		FormsEntity formFound = formsRepository.findById(formId)
				.orElseThrow(() -> new EntityNotFoundException("Form with id " + formId + " not found"));

		// Delete all the sections and form fields
		List<SectionsEntity> sectionsEntityList = formFound.getSectionsEntityList();
		for (int i = 0; i < sectionsEntityList.size(); i++) {
			SectionsEntity sectionsEntity = sectionsEntityList.get(i);
			List<FormFieldsEntity> formFieldsEntityList = sectionsEntity.getFormFieldsEntityList();
			for (int j = 0; j < formFieldsEntityList.size(); j++) {
				FormFieldsEntity formFieldsEntity = formFieldsEntityList.get(j);
				formFieldsRepository.delete(formFieldsEntity);
			}
			sectionsRepository.delete(sectionsEntity);
		}

		// Delete all the predfined that is not used form fields in the form
		List<FormFieldsEntity> formFieldsEntityList = formFound.getFormFieldsEntityList();
		for (int i = 0; i < formFieldsEntityList.size(); i++) {
			FormFieldsEntity formFieldsEntity = formFieldsEntityList.get(i);
			formFieldsRepository.delete(formFieldsEntity);
		}

		// Delete section from form found
		formFound.getSectionsEntityList().clear();
		// Delete all the form fields
		formFound.getFormFieldsEntityList().clear();

		// Create new section and form fields based on existing form Id
		List<FormFieldDTO> formFieldList = formRequest.getFormFieldsList();
		List<FormSchemaDTO> formSchemaList = formRequest.getFormSchemaList();

		System.out.println("Form Field List Length: " + formFieldList.size());
		System.out.println("Form Schema List Length: " + formSchemaList.size());

		int totalSections = formSchemaList.size();
		System.out.println("Total Sections: " + totalSections);
		// Create form section -> form field
		for (int i = 0; i < totalSections; i++) {
			FormSchemaDTO formSchemaDTO = formSchemaList.get(i);
			List<DroppableZone> droppableZones = formSchemaList.get(i).getDroppableZones();
			SectionsEntity savedSection = mapSectionDTOToSectionEntity(formSchemaDTO, formFound, i + 1);
			for (int j = 0; j < droppableZones.size(); j++) {
				List<String> fieldIds = droppableZones.get(j).getFieldIds();
				System.out.println("Form Row: " + (i + 1) + " Form Column: " + (j + 1));
				System.out.println("Field ID List Length: " + fieldIds.size());
				for (int k = 0; k < fieldIds.size(); k++) {
					String fieldId = fieldIds.get(k);
					System.out.println("Field ID: " + fieldIds.get(k));
					// Find the field in the form field list
					FormFieldDTO formFieldDTO = formFieldList.stream()
							.filter(formField -> formField.getFieldId().equals(fieldId)).findFirst().orElse(null);
					System.out.println("Form Field: " + formFieldDTO.getLabel());
					mapFormFieldDTOToFormFieldEntity(formFieldDTO, formFound, savedSection, j + 1, k + 1);
				}
			}
		}

		// Set predefined not used into form field
		formFieldList.forEach((formField) -> {
			if (formField.getFieldType().equals("predefined") && formField.getIsUsed() == false) {
				FormFieldsEntity formFieldsEntity = new FormFieldsEntity();
				formFieldsEntity.setLabel(formField.getLabel());
				formFieldsEntity.setType(formField.getType());
				formFieldsEntity.setPlaceholder(formField.getPlaceholder());
				formFieldsEntity.setIsActive(false);
				formFieldsEntity.setMinLength(formField.getMinLength());
				formFieldsEntity.setMaxLength(formField.getMaxLength());
				formFieldsEntity.setIsRequired(formField.getRequired());
				formFieldsEntity.setFieldName(formField.getName());
				formFieldsEntity.setColumnOrder(0);
				formFieldsEntity.setColumnNumber(0);
				formFieldsEntity.setFieldId(formField.getFieldId());
				formFieldsEntity.setFieldType(formField.getFieldType());
				formFieldsEntity.setFormsEntity(formFound);
				formFieldsEntity.setSectionsEntity(null);
				formFieldsEntity.setButtonText(formField.getButtonText());
				formFieldsEntity.setButtonType(formField.getButtonType());
				formFieldsEntity.setButtonName(formField.getButtonName());
				formFieldsEntity.setButtonClass(formField.getButtonClass());
				formFieldsEntity.setButtonLocation(formField.getButtonLocation());
				// More Fields
				formFieldsEntity.setRequiredErrorMessage(formField.getRequiredErrorMessage());
				formFieldsEntity.setMinLengthErrorMessage(formField.getMinLengthErrorMessage());
				formFieldsEntity.setMaxLengthErrorMessage(formField.getMaxLengthErrorMessage());
				formFieldsEntity.setRegexErrorMessage(formField.getPatternValidationErrorMessage());
				formFieldsEntity.setEmailValidation(formField.getEmailValidation());
				formFieldsEntity.setEmailValidationErrorMessage(formField.getEmailValidationErrorMessage());
				formFieldsEntity.setMaxValue(formField.getMaxValue());
				formFieldsEntity.setMaxValueErrorMessage(formField.getMaxValueErrorMessage());
				formFieldsEntity.setMinValue(formField.getMinValue());
				formFieldsEntity.setMinValueErrorMessage(formField.getMinValueErrorMessage());
				formFieldsEntity.setFileTypeValidation(formField.getFileTypeValidation());
				formFieldsEntity.setFileTypeValidationErrorMessage(formField.getFileTypeValidationErrorMessage());
				formFieldsEntity.setFileSizeValidation(formField.getFileSizeValidation());
				formFieldsEntity.setFileSizeValidationErrorMessage(formField.getFileSizeValidationErrorMessage());
				formFieldsEntity.setOptions(formField.getOptions());
				formFieldsEntity.setParent(formField.getParent());
				formFieldsEntity.setVisible(formField.getVisible());
				formFieldsEntity.setUserGroup(formField.getUserGroup());
				formFieldsEntity.setTableConfig(formField.getTableConfig());
				formFieldsEntity.setTableSetting(formField.getTableSetting());
				formFieldsEntity.setCountryOptions(formField.getCountryOptions());
				formFieldsEntity.setSubName(formField.getSubName());
				formFieldsEntity.setCopyFields(formField.getCopyFields());
				formFieldsEntity.setFieldLocation(formField.getFieldLocation());
				formFieldsEntity.setFieldSize(formField.getFieldSize());
				formFieldsEntity.setWordSize(formField.getWordSize());
				formFieldsEntity.setWordText(formField.getWordText());

				// Added 15112023
				formFieldsEntity.setList(formField.getList());

				// Added 16112023
				formFieldsEntity.setConditionValidation(formField.getConditionValidation());
				formFieldsEntity.setConditionValidationErrorMessage(formField.getConditionValidationErrorMessage());

				// Added 26122023
				formFieldsEntity.setFormCategorySelect(formField.getFormCategorySelect());
				formFieldsEntity.setInformation(formField.getInformation());
				formFieldsEntity.setInformationText(formField.getInformationText());

				formFieldsRepository.save(formFieldsEntity);
			}
		});

		// Update form name
		formFound.setFormName(formRequest.getFormName());
		formFound.setFormType(formRequest.getFormType());
		formFound.setEntityType(formRequest.getEntityType());
		formFound.setStepperNumber(formRequest.getStepperNumber());
		// Update base form
		if (formRequest.getBaseFormId() != null && formRequest.getBaseFormId() > 0) {
			FormsEntity baseForm = formsRepository.findById(formRequest.getBaseFormId()).orElseThrow(
					() -> new EntityNotFoundException("Form with id " + formRequest.getBaseFormId() + " not found"));
			System.out.println("Base Form: " + baseForm.getFormName());
			formFound.setBaseForm(baseForm);
		} else {
			formFound.setBaseForm(null);
		}

		// Added - 27102023
		formFound.setFormCategory(formRequest.getFormCategory());

		FormsEntity updatedForm = formsRepository.save(formFound);

		return null;
	}

	/**
	 * This method is used to delete a form
	 *
	 * @param formId
	 */
	@Override
	public void deleteForm(int formId) {
		FormsEntity formFound = formsRepository.findById(formId)
				.orElseThrow(() -> new EntityNotFoundException("Form with id " + formId + " not found"));

		// Delete all the sections and form fields
		List<SectionsEntity> sectionsEntityList = formFound.getSectionsEntityList();
		for (int i = 0; i < sectionsEntityList.size(); i++) {
			SectionsEntity sectionsEntity = sectionsEntityList.get(i);
			List<FormFieldsEntity> formFieldsEntityList = sectionsEntity.getFormFieldsEntityList();
			for (int j = 0; j < formFieldsEntityList.size(); j++) {
				FormFieldsEntity formFieldsEntity = formFieldsEntityList.get(j);
				formFieldsRepository.delete(formFieldsEntity);
			}
			sectionsRepository.delete(sectionsEntity);
		}
		formsRepository.delete(formFound);
	}

	@Override
	public List<FormListResponse> getBaseForms() {
		List<FormsEntity> formsEntityList = formsRepository.getBaseForm();
		return formsEntityList.stream().map(formsEntity -> {
			FormListResponse formListResponse = new FormListResponse();
			formListResponse.setFormId(formsEntity.getId());
			formListResponse.setFormName(formsEntity.getFormName());
			return formListResponse;
		}).toList();
	}

	@Override
	public FormResponseDTO getFormByName(String formName) {
		FormsEntity formFound = formsRepository.findByFormName(formName)
				.orElseThrow(() -> new EntityNotFoundException("Form with name " + formName + " not found"));
		return formsEntityToFormResponseDTO(formFound);
	}

	@Override
	public FormListingResponseDTO getFormListingPage(Integer page, Integer size, String sortBy, String sortDirection) {
		System.out.println("Sorting");
		Sort sort = null;
		System.out.println("Sort By: " + sortBy);
		if (sortBy != null) {
			// Get direction based on sort direction
			Sort.Direction direction = Sort.DEFAULT_DIRECTION;
			if (sortDirection != null) {
				direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			}
			sort = Sort.by(direction, sortBy);
		} else {
			sort = Sort.by(Sort.Direction.DESC, "updatedAt");
		}
		System.out.println("Test 3");
		Pageable pageable = null;
		if (page == null && size == null) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
		} else {
			pageable = PageRequest.of(page, size, sort);
		}
		Page<FormsEntity> accountsPage = formsRepository.findAllByPaginationAndSort(pageable);

		return formPageToFormListingDTO(accountsPage);
	}

	@Override
	public FormListingResponseDTO getFormListingPageWithSearch(Integer page, Integer size, String sortBy,
			String sortDirection, String searchTerm) {
		System.out.println("Sorting With Search");
		Sort sort = null;
		if (sortBy != null) {
			// Get direction based on sort direction
			Sort.Direction direction = Sort.DEFAULT_DIRECTION;
			if (sortDirection != null) {
				direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			}
			sort = Sort.by(direction, sortBy);
		} else {
			sort = Sort.by(Sort.Direction.DESC, "updatedAt");
		}

		Pageable pageable = null;
		if (page == null && size == null) {
			pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
		} else {
			pageable = PageRequest.of(page, size, sort);
		}

		// Dynamic search based on custom view (future feature)
		List<String> customView = List.of("id", "formName", "entityType", "baseFormName", "updatedAt", "formType",
				"formCategory");
		Specification<FormsEntity> specification = (root, query, criteriaBuilder) -> {

			List<Predicate> predicates = new ArrayList<>();

			// Custom fields you want to search in
			for (String field : customView) {
				if ("baseFormName".equals(field)) {
					Join<FormsEntity, FormsEntity> baseJoin = root.join("baseForm", JoinType.LEFT);
					predicates.add(criteriaBuilder.like(criteriaBuilder.lower(baseJoin.get("formName")),
							"%" + searchTerm.toLowerCase() + "%"));
				} else {
					Path<Object> fieldPath = root.get(field);

					if (fieldPath.getJavaType() == Integer.class) {
						try {
							Integer id = Integer.parseInt(searchTerm);
							predicates.add(criteriaBuilder.equal(fieldPath, id));
						} catch (NumberFormatException e) {
							// Ignore if it's not a valid integer
						}
					} else {
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(fieldPath.as(String.class)),
								"%" + searchTerm.toLowerCase() + "%"));
					}
				}
			}
			Predicate searchOrPredicates = criteriaBuilder.or(predicates.toArray(new Predicate[0]));

			return criteriaBuilder.and(searchOrPredicates);
		};

		Page<FormsEntity> formsPage = formsRepository.findAll(specification, pageable);

		return formPageToFormListingDTO(formsPage);
	}

	@Override
	public List<String> getFormCategories() {
		return formsRepository.getFormCategories();
	}

	@Override
	public FormTemplateNamesListResponseDTO getFormsTemplateNamesByCategory(String formCategory) {
		List<FormTemplateNamesResponseDTO> formTemplateNamesData = null;
		if (formCategory.equals("ALL")) {
			formTemplateNamesData = formsRepository.findAll().stream().map(formsEntity -> {
				FormTemplateNamesResponseDTO formTemplateNamesResponseDTO = new FormTemplateNamesResponseDTO();
				formTemplateNamesResponseDTO.setFormId(formsEntity.getId());
				formTemplateNamesResponseDTO.setFormName(formsEntity.getFormName());
				return formTemplateNamesResponseDTO;
			}).toList();
		} else {
			formTemplateNamesData = formsRepository.getFormsByFormCategory(formCategory).stream().map(formsEntity -> {
				FormTemplateNamesResponseDTO formTemplateNamesResponseDTO = new FormTemplateNamesResponseDTO();
				formTemplateNamesResponseDTO.setFormId(formsEntity.getId());
				formTemplateNamesResponseDTO.setFormName(formsEntity.getFormName());
				return formTemplateNamesResponseDTO;
			}).toList();
		}
		FormTemplateNamesListResponseDTO formTemplateNamesListResponseDTO = new FormTemplateNamesListResponseDTO();
		formTemplateNamesListResponseDTO.setCategory(formCategory);
		formTemplateNamesListResponseDTO.setFormTemplateNamesList(formTemplateNamesData);
		return formTemplateNamesListResponseDTO;
	}

	@Override
	public Map<String, Integer> getFormTemplateNamesIdMap(List<String> formTemplateNames) {
		Map<String, Integer> formTemplateNamesIdMap = new HashMap<>();
		List<FormsEntity> formsEntityList = formsRepository.getFormsByFormTemplateNames(formTemplateNames);
		for (int i = 0; i < formsEntityList.size(); i++) {
			FormsEntity formsEntity = formsEntityList.get(i);
			formTemplateNamesIdMap.put(formsEntity.getFormName(), formsEntity.getId());
		}
		return formTemplateNamesIdMap;
	}

	private FormListingResponseDTO formPageToFormListingDTO(Page<FormsEntity> formEntitiesPage) {
		FormListingResponseDTO formListingResponseDTO = new FormListingResponseDTO();
		formListingResponseDTO.setTotalPages(formEntitiesPage.getTotalPages());
		formListingResponseDTO.setTotalElements(formEntitiesPage.getTotalElements());
		formListingResponseDTO.setPage(formEntitiesPage.getNumber());
		formListingResponseDTO.setPageSize(formEntitiesPage.getSize());

		List<FormListResponse> formListResponseList = formEntitiesPage.getContent().stream().map(formsEntity -> {
			FormListResponse formListResponse = new FormListResponse();
			formListResponse.setFormId(formsEntity.getId());
			formListResponse.setFormName(formsEntity.getFormName());
			formListResponse.setFormType(formsEntity.getFormType());
			formListResponse.setEntityType(formsEntity.getEntityType());
			formListResponse.setStepperNumber(formsEntity.getStepperNumber());
			if (formsEntity.getBaseForm() != null) {
				formListResponse.setBaseFormName(formsEntity.getBaseForm().getFormName());
			} else {
				formListResponse.setBaseFormName("");
			}
			formListResponse.setUpdatedBy("Avensys");
			formListResponse.setUpdatedAt(formsEntity.getUpdatedAt());

			// Added -27102023
			formListResponse.setFormCategory(formsEntity.getFormCategory());

			return formListResponse;
		}).toList();
		formListingResponseDTO.setForms(formListResponseList);
		return formListingResponseDTO;
	}

	/**
	 * This method is used to map the form field DTO to form field entity
	 *
	 * @param formsEntity
	 * @return
	 */
	private FormResponseDTO formsEntityToFormResponseDTO(FormsEntity formsEntity) {
		List<FormSchemaDTO> formSchemaList = formsEntityToFormSchemaListDTO(formsEntity);
		List<FormFieldDTO> formFieldDTOList = formsEntityToFormFieldDTOList(formsEntity);
		FormResponseDTO formResponseDTO = new FormResponseDTO();
		formResponseDTO.setFormId(formsEntity.getId());
		formResponseDTO.setFormName(formsEntity.getFormName());
		formResponseDTO.setFormType(formsEntity.getFormType());
		if (formsEntity.getBaseForm() != null) {
			formResponseDTO.setBaseFormId(formsEntity.getBaseForm().getId());
		} else {
			formResponseDTO.setBaseFormId(0);
		}
		formResponseDTO.setEntityType(formsEntity.getEntityType());
		formResponseDTO.setStepperNumber(formsEntity.getStepperNumber());
		formResponseDTO.setFormFieldsList(formFieldDTOList);
		formResponseDTO.setFormSchemaList(formSchemaList);

		// Added -27102023
		formResponseDTO.setFormCategory(formsEntity.getFormCategory());

		return formResponseDTO;
	}

	/**
	 * This method is used to map the form entity to form response DTO
	 *
	 * @param formsEntity
	 * @return
	 */
	private List<FormSchemaDTO> formsEntityToFormSchemaListDTO(FormsEntity formsEntity) {
		// Get section sorted by section order ascending order
		List<SectionsEntity> sectionsFound = formsEntity.getSectionsEntityList();
		sectionsFound.sort(Comparator.comparing(SectionsEntity::getSectionOrder));
		System.out.println("Section List Length: " + sectionsFound.size());

		List<FormSchemaDTO> formSchemaList = new ArrayList<>();
		for (int i = 0; i < sectionsFound.size(); i++) {
			SectionsEntity sectionsEntity = sectionsFound.get(i);
			List<FormFieldsEntity> formFieldsEntityList = sectionsEntity.getFormFieldsEntityList();
			formFieldsEntityList.sort(Comparator.comparing(FormFieldsEntity::getColumnOrder));
			System.out.println("Form Field List Length: " + formFieldsEntityList.size());
			FormSchemaDTO formSchemaDTO = new FormSchemaDTO();
			formSchemaDTO.setTitle(sectionsEntity.getName());
			formSchemaDTO.setRowId(sectionsEntity.getSectionOrder());
			formSchemaDTO.setIsTitle(sectionsEntity.getIsTitle());
			List<DroppableZone> droppableZones = new ArrayList<>();
			for (int j = 0; j < sectionsEntity.getNoOfColumn(); j++) {
				DroppableZone droppableZone = new DroppableZone();
				droppableZone.setId((i + 1) + "_" + j);
				int colNo = j + 1;
				List<FormFieldsEntity> sectionFormFields = formFieldsEntityList.stream()
						.filter(formFieldsEntity -> formFieldsEntity.getColumnNumber() == colNo).toList();
				System.out.println("Section Form Field List Length: " + sectionFormFields.size());
				List<String> fieldIds = sectionFormFields.stream().map(FormFieldsEntity::getFieldId).toList();
				droppableZone.setFieldIds(fieldIds);
				droppableZones.add(droppableZone);
			}
			formSchemaDTO.setDroppableZones(droppableZones);
			formSchemaList.add(formSchemaDTO);
		}
		// Get FormFields from all the sections
		List<FormFieldsEntity> formFieldsEntityList = formsEntity.getFormFieldsEntityList();
		// Arrange from column order ascending order
		formFieldsEntityList.sort(Comparator.comparing(FormFieldsEntity::getColumnOrder));
		List<FormFieldDTO> formFieldDTOList = formFieldsEntityList.stream().map(formFieldsEntity -> {
			FormFieldDTO formFieldDTO = new FormFieldDTO();
			formFieldDTO.setLabel(formFieldsEntity.getLabel());
			formFieldDTO.setType(formFieldsEntity.getType());
			formFieldDTO.setPlaceholder(formFieldsEntity.getPlaceholder());
			formFieldDTO.setName(formFieldsEntity.getFieldName());
//                    formFieldDTO.setIsActive(formFieldsEntity.getIsActive());
//                    formFieldDTO.setRegex(formFieldsEntity.getRegex());
			formFieldDTO.setMinLength(formFieldsEntity.getMinLength());
			formFieldDTO.setMaxLength(formFieldsEntity.getMaxLength());
			formFieldDTO.setRequired(formFieldsEntity.getIsRequired());
//                    formFieldDTO.setHelp(formFieldsEntity.getHelp());
//                    formFieldDTO.setCustomAttributes(formFieldsEntity.getCustomAttributes());
//                    formFieldDTO.setFieldName(formFieldsEntity.getFieldName());
//                    formFieldDTO.setIsDisabled(formFieldsEntity.getIsDisabled());
//                    formFieldDTO.setColumnOrder(formFieldsEntity.getColumnOrder());
//                    formFieldDTO.setColumnNumber(formFieldsEntity.getColumnNumber());
			formFieldDTO.setFieldId(formFieldsEntity.getFieldId());
			formFieldDTO.setFieldType(formFieldsEntity.getFieldType());
			formFieldDTO.setWordSize(formFieldsEntity.getWordSize());
			formFieldDTO.setWordText(formFieldsEntity.getWordText());

			// Added 15112023
			formFieldDTO.setList(formFieldsEntity.getList());

			// Added 16112023
			formFieldDTO.setConditionValidation(formFieldsEntity.getConditionValidation());
			formFieldDTO.setConditionValidationErrorMessage(formFieldsEntity.getConditionValidationErrorMessage());

			// Added 26122023
			formFieldDTO.setFormCategorySelect(formFieldsEntity.getFormCategorySelect());
			formFieldDTO.setInformation(formFieldsEntity.getInformation());
			formFieldDTO.setInformationText(formFieldsEntity.getInformationText());

			return formFieldDTO;
		}).toList();

		return formSchemaList;
	}

	/**
	 * This method is used to map the form entity to form field DTO
	 *
	 * @param formsEntity
	 * @return
	 */
	private List<FormFieldDTO> formsEntityToFormFieldDTOList(FormsEntity formsEntity) {
		// Get FormFields from all the sections
		List<FormFieldsEntity> formFieldsEntityList = formsEntity.getFormFieldsEntityList();
		List<FormFieldDTO> formFieldDTOList = formFieldsEntityList.stream().map(formFieldsEntity -> {
			FormFieldDTO formFieldDTO = new FormFieldDTO();
			formFieldDTO.setLabel(formFieldsEntity.getLabel());
			formFieldDTO.setType(formFieldsEntity.getType());
			formFieldDTO.setPlaceholder(formFieldsEntity.getPlaceholder());
			formFieldDTO.setName(formFieldsEntity.getFieldName());
			formFieldDTO.setIsUsed(formFieldsEntity.getIsActive());
			formFieldDTO.setPattern(formFieldsEntity.getRegex());
			formFieldDTO.setMinLength(formFieldsEntity.getMinLength());
			formFieldDTO.setMaxLength(formFieldsEntity.getMaxLength());
			formFieldDTO.setRequired(formFieldsEntity.getIsRequired());
//                    formFieldDTO.setHelp(formFieldsEntity.getHelp());
//                    formFieldDTO.setCustomAttributes(formFieldsEntity.getCustomAttributes());
//                    formFieldDTO.setFieldName(formFieldsEntity.getFieldName());
//                    formFieldDTO.setIsDisabled(formFieldsEntity.getIsDisabled());
//                    formFieldDTO.setColumnOrder(formFieldsEntity.getColumnOrder());
//                    formFieldDTO.setColumnNumber(formFieldsEntity.getColumnNumber());
			formFieldDTO.setFieldId(formFieldsEntity.getFieldId());
			formFieldDTO.setFieldType(formFieldsEntity.getFieldType());
			formFieldDTO.setButtonClass(formFieldsEntity.getButtonClass());
			formFieldDTO.setButtonName(formFieldsEntity.getButtonName());
			formFieldDTO.setButtonType(formFieldsEntity.getButtonType());
			formFieldDTO.setButtonText(formFieldsEntity.getButtonText());
			formFieldDTO.setButtonLocation(formFieldsEntity.getButtonLocation());

			// More fields 2
			formFieldDTO.setRequiredErrorMessage(formFieldsEntity.getRequiredErrorMessage());
			formFieldDTO.setMinLengthErrorMessage(formFieldsEntity.getMinLengthErrorMessage());
			formFieldDTO.setMaxLengthErrorMessage(formFieldsEntity.getMaxLengthErrorMessage());
			formFieldDTO.setPatternValidationErrorMessage(formFieldsEntity.getRegexErrorMessage());
			formFieldDTO.setEmailValidation(formFieldsEntity.getEmailValidation());
			formFieldDTO.setEmailValidationErrorMessage(formFieldsEntity.getEmailValidationErrorMessage());
			formFieldDTO.setMaxValue(formFieldsEntity.getMaxValue());
			formFieldDTO.setMaxValueErrorMessage(formFieldsEntity.getMaxValueErrorMessage());
			formFieldDTO.setMinValue(formFieldsEntity.getMinValue());
			formFieldDTO.setMinValueErrorMessage(formFieldsEntity.getMinValueErrorMessage());
			formFieldDTO.setFileTypeValidation(formFieldsEntity.getFileTypeValidation());
			formFieldDTO.setFileTypeValidationErrorMessage(formFieldsEntity.getFileTypeValidationErrorMessage());
			formFieldDTO.setFileSizeValidation(formFieldsEntity.getFileSizeValidation());
			formFieldDTO.setFileSizeValidationErrorMessage(formFieldsEntity.getFileSizeValidationErrorMessage());
			formFieldDTO.setOptions(formFieldsEntity.getOptions());
			formFieldDTO.setParent(formFieldsEntity.getParent());
			formFieldDTO.setVisible(formFieldsEntity.getVisible());
			formFieldDTO.setUserGroup(formFieldsEntity.getUserGroup());
			formFieldDTO.setTableConfig(formFieldsEntity.getTableConfig());
			formFieldDTO.setTableSetting(formFieldsEntity.getTableSetting());
//                    formFieldDTO.setTableEdit(formFieldsEntity.getTableEdit());
//                    formFieldDTO.setTableDelete(formFieldsEntity.getTableDelete());
//                    formFieldDTO.setTableAPI(formFieldsEntity.getTableAPI());
//                    formFieldDTO.setTableAPIURL(formFieldsEntity.getTableAPIURL());
			formFieldDTO.setCountryOptions(formFieldsEntity.getCountryOptions());
			formFieldDTO.setSubName(formFieldsEntity.getSubName());
			formFieldDTO.setCopyFields(formFieldsEntity.getCopyFields());
			formFieldDTO.setFieldLocation(formFieldsEntity.getFieldLocation());
			formFieldDTO.setFieldSize(formFieldsEntity.getFieldSize());
			formFieldDTO.setWordSize(formFieldsEntity.getWordSize());
			formFieldDTO.setWordText(formFieldsEntity.getWordText());

			// Added 15112023
			formFieldDTO.setList(formFieldsEntity.getList());

			// Added 16112023
			formFieldDTO.setConditionValidation(formFieldsEntity.getConditionValidation());
			formFieldDTO.setConditionValidationErrorMessage(formFieldsEntity.getConditionValidationErrorMessage());

			// Added 26122023
			formFieldDTO.setFormCategorySelect(formFieldsEntity.getFormCategorySelect());
			formFieldDTO.setInformation(formFieldsEntity.getInformation());
			formFieldDTO.setInformationText(formFieldsEntity.getInformationText());

			return formFieldDTO;
		}).toList();
		return formFieldDTOList;
	}

	/**
	 * This method is used to map the form request DTO to form entity
	 *
	 * @param formRequest
	 * @param userId
	 * @return
	 */
	private FormsEntity mapFormRequestDTOToFormEntity(FormRequestDTO formRequest, int userId) {
		FormsEntity formsEntity = new FormsEntity();
		formsEntity.setFormName(formRequest.getFormName());
		formsEntity.setUserId(userId);
		formsEntity.setFormType(formRequest.getFormType());
		formsEntity.setEntityType(formRequest.getEntityType());
		formsEntity.setStepperNumber(formRequest.getStepperNumber());
		formsEntity.setFormCategory(formRequest.getFormCategory());

		if (formRequest.getBaseFormId() != null && formRequest.getBaseFormId() > 0) {
			FormsEntity baseForm = formsRepository.findById(formRequest.getBaseFormId()).orElseThrow(
					() -> new EntityNotFoundException("Form with id " + formRequest.getBaseFormId() + " not found"));
			System.out.println("Base Form: " + baseForm.getFormName());
			formsEntity.setBaseForm(baseForm);
		} else {
			formsEntity.setBaseForm(null);
		}

		return formsRepository.save(formsEntity);
	}

	/**
	 * This method is used to map the section DTO to section entity
	 *
	 * @param sectionDTO
	 * @param formsEntity
	 * @param sectionOrder
	 * @return
	 */
	private SectionsEntity mapSectionDTOToSectionEntity(FormSchemaDTO sectionDTO, FormsEntity formsEntity,
			int sectionOrder) {
		SectionsEntity sectionEntity = new SectionsEntity();
		sectionEntity.setFormsEntity(formsEntity);
		sectionEntity.setName(sectionDTO.getTitle());
		sectionEntity.setIsTitle(sectionDTO.getIsTitle());
		sectionEntity.setSectionOrder(sectionOrder);
		sectionEntity.setNoOfColumn(sectionDTO.getDroppableZones().size());
		return sectionsRepository.save(sectionEntity);
	}

	/**
	 * This method is used to map the form field DTO to form field entity
	 *
	 * @param formFieldDTO
	 * @param formsEntity
	 * @param sectionsEntity
	 * @param columnNumber
	 * @param columnOrder
	 * @return
	 */
	private FormFieldsEntity mapFormFieldDTOToFormFieldEntity(FormFieldDTO formFieldDTO, FormsEntity formsEntity,
			SectionsEntity sectionsEntity, int columnNumber, int columnOrder) {
		FormFieldsEntity formFieldsEntity = new FormFieldsEntity();
		formFieldsEntity.setLabel(formFieldDTO.getLabel());
		formFieldsEntity.setType(formFieldDTO.getType());
		formFieldsEntity.setPlaceholder(formFieldDTO.getPlaceholder());
		formFieldsEntity.setIsActive(formFieldDTO.getIsUsed());
		formFieldsEntity.setRegex(formFieldDTO.getPattern());
		formFieldsEntity.setMinLength(formFieldDTO.getMinLength());
		formFieldsEntity.setMaxLength(formFieldDTO.getMaxLength());
		formFieldsEntity.setIsRequired(formFieldDTO.getRequired());
//        formFieldsEntity.setHelp(formFieldDTO.getHelp());
//        formFieldsEntity.setCustomAttributes(formFieldDTO.getCustomAttributes());
		formFieldsEntity.setFieldName(formFieldDTO.getName());
//        formFieldsEntity.setIsDisabled(formFieldDTO.getIsDisabled());
		formFieldsEntity.setColumnOrder(columnOrder);
		formFieldsEntity.setColumnNumber(columnNumber);
		formFieldsEntity.setFieldId(formFieldDTO.getFieldId());
		formFieldsEntity.setFieldType(formFieldDTO.getFieldType());
		formFieldsEntity.setFormsEntity(formsEntity);
		formFieldsEntity.setSectionsEntity(sectionsEntity);
		formFieldsEntity.setButtonText(formFieldDTO.getButtonText());
		formFieldsEntity.setButtonType(formFieldDTO.getButtonType());
		formFieldsEntity.setButtonName(formFieldDTO.getButtonName());
		formFieldsEntity.setButtonClass(formFieldDTO.getButtonClass());
		formFieldsEntity.setButtonLocation(formFieldDTO.getButtonLocation());
		// More fields 2
		formFieldsEntity.setRequiredErrorMessage(formFieldDTO.getRequiredErrorMessage());
		formFieldsEntity.setMinLengthErrorMessage(formFieldDTO.getMinLengthErrorMessage());
		formFieldsEntity.setMaxLengthErrorMessage(formFieldDTO.getMaxLengthErrorMessage());
		formFieldsEntity.setRegexErrorMessage(formFieldDTO.getPatternValidationErrorMessage());
		formFieldsEntity.setEmailValidation(formFieldDTO.getEmailValidation());
		formFieldsEntity.setEmailValidationErrorMessage(formFieldDTO.getEmailValidationErrorMessage());
		formFieldsEntity.setMaxValue(formFieldDTO.getMaxValue());
		formFieldsEntity.setMaxValueErrorMessage(formFieldDTO.getMaxValueErrorMessage());
		formFieldsEntity.setMinValue(formFieldDTO.getMinValue());
		formFieldsEntity.setMinValueErrorMessage(formFieldDTO.getMinValueErrorMessage());
		formFieldsEntity.setFileTypeValidation(formFieldDTO.getFileTypeValidation());
		formFieldsEntity.setFileTypeValidationErrorMessage(formFieldDTO.getFileTypeValidationErrorMessage());
		formFieldsEntity.setFileSizeValidation(formFieldDTO.getFileSizeValidation());
		formFieldsEntity.setFileSizeValidationErrorMessage(formFieldDTO.getFileSizeValidationErrorMessage());
		formFieldsEntity.setOptions(formFieldDTO.getOptions());
		formFieldsEntity.setParent(formFieldDTO.getParent());
		formFieldsEntity.setVisible(formFieldDTO.getVisible());
		formFieldsEntity.setUserGroup(formFieldDTO.getUserGroup());
		formFieldsEntity.setTableConfig(formFieldDTO.getTableConfig());
		formFieldsEntity.setTableSetting(formFieldDTO.getTableSetting());
		formFieldsEntity.setCountryOptions(formFieldDTO.getCountryOptions());
		formFieldsEntity.setSubName(formFieldDTO.getSubName());
		formFieldsEntity.setCopyFields(formFieldDTO.getCopyFields());
		formFieldsEntity.setFieldLocation(formFieldDTO.getFieldLocation());
		formFieldsEntity.setFieldSize(formFieldDTO.getFieldSize());
		formFieldsEntity.setWordSize(formFieldDTO.getWordSize());
		formFieldsEntity.setWordText(formFieldDTO.getWordText());

		// Added 15112023
		formFieldsEntity.setList(formFieldDTO.getList());

		// Added 16112023
		formFieldsEntity.setConditionValidation(formFieldDTO.getConditionValidation());
		formFieldsEntity.setConditionValidationErrorMessage(formFieldDTO.getConditionValidationErrorMessage());

		// Added 26122023
		formFieldsEntity.setFormCategorySelect(formFieldDTO.getFormCategorySelect());
		formFieldsEntity.setInformation(formFieldDTO.getInformation());
		formFieldsEntity.setInformationText(formFieldDTO.getInformationText());

		return formFieldsRepository.save(formFieldsEntity);
	}
}
