package com.avensys.rts.formservice.payloadresponse;

import com.avensys.rts.formservice.payload.FormFieldDTO;
import com.avensys.rts.formservice.payload.FormSchemaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormResponseDTO {
    private Integer formId;
    private String formName;
    private String formType;
    private Integer baseFormId;
    private String entityType;
    private Integer stepperNumber;
    List<FormFieldDTO> formFieldsList;
    List<FormSchemaDTO> formSchemaList;
}
