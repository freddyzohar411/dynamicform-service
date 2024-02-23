package com.avensys.rts.formservice.payloadresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormTemplateNamesResponseDTO {
	private Integer formId;
	private String formName;
}
