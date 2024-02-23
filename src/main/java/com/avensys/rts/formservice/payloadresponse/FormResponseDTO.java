package com.avensys.rts.formservice.payloadresponse;

import java.util.List;

import com.avensys.rts.formservice.payload.FormFieldDTO;
import com.avensys.rts.formservice.payload.FormSchemaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormResponseDTO {
	private Integer formId;
	private String formName;
	private String formType;
	private String formCategory;
	private Integer baseFormId;
	private String entityType;
	private Integer stepperNumber;
	List<FormFieldDTO> formFieldsList;
	List<FormSchemaDTO> formSchemaList;
}
