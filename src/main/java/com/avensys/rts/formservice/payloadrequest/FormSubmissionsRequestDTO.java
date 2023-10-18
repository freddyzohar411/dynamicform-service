package com.avensys.rts.formservice.payloadrequest;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSubmissionsRequestDTO {
    private Integer formId;
    private Integer userId;
    private JsonNode submissionData;
    private Integer entityId;
    private String entityType;
}
