package com.avensys.rts.formservice.payloadresponse;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormListResponse {
	private Integer formId;
	private String formName;
	private String formType;
	private String formCategory;
	private String entityType;
	private Integer stepperNumber;
	private String baseFormName;
	private String updatedBy;
	private LocalDateTime updatedAt;
}
