package com.avensys.rts.formservice.payloadresponse;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormSubmissionsResponseDTO {
	private Integer id;
	private Integer formId;
	private Integer userId;
	private JsonNode submissionData;
	private Integer entityId;
	private String entityType;
}
