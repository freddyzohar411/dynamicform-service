package com.avensys.rts.formservice.payloadresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormListResponse {
    private Integer formId;
    private String formName;
    private String formType;
    private String entityType;
    private Integer stepperNumber;
    private String baseFormName;
    private String modifiedBy;
    private LocalDateTime modifiedAt;
}
