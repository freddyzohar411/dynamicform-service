package com.avensys.rts.formservice.payloadrequest;

import com.avensys.rts.formservice.payload.FormFieldDTO;
import com.avensys.rts.formservice.payload.FormSchemaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
