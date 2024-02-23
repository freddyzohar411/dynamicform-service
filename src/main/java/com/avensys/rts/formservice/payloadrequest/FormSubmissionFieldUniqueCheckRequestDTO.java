package com.avensys.rts.formservice.payloadrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmissionFieldUniqueCheckRequestDTO {
	private Long formId;
	private String fieldName;
	private String fieldValue;
}
