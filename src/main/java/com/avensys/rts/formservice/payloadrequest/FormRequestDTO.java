package com.avensys.rts.formservice.payloadrequest;

import java.util.List;

import com.avensys.rts.formservice.payload.FormFieldDTO;
import com.avensys.rts.formservice.payload.FormSchemaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormRequestDTO {
	private String formName;
	private String formType;
	private String formCategory;
	private Integer baseFormId;
	private String entityType;
	private Integer stepperNumber;
	List<FormFieldDTO> formFieldsList;
	List<FormSchemaDTO> formSchemaList;
}
