package com.avensys.rts.formservice.payloadresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormTemplateNamesResponseDTO {
	private Integer formId;
	private String formName;
}
