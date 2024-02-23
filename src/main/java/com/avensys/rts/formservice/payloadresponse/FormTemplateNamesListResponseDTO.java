package com.avensys.rts.formservice.payloadresponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormTemplateNamesListResponseDTO {
	private String category;
	private List<FormTemplateNamesResponseDTO> formTemplateNamesList;
}
