package com.avensys.rts.formservice.payloadresponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormTemplateNamesListResponseDTO {
	private String category;
	private List<FormTemplateNamesResponseDTO> formTemplateNamesList;
}
