package com.avensys.rts.formservice.payloadresponse;

import com.avensys.rts.formservice.entity.FormsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormListingResponseDTO {
    private Integer totalPages;
    private Long totalElements;
    private Integer page;
    private Integer pageSize;
    private List<FormListResponse> forms;
}
