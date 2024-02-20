package com.avensys.rts.formservice.payloadrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmissionFieldUniqueCheckRequestDTO {
	private Long formId;
	private String fieldName;
	private String fieldValue;
}
