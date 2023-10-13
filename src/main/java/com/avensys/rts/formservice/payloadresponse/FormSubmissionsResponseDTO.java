package com.avensys.rts.formservice.payloadresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSubmissionsResponseDTO {
    private Integer id;
    private Integer formId;
    private String submissionData;
    private Integer entityId;
    private String entityType;
}
