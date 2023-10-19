package com.avensys.rts.formservice.payloadrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormListingRequestDTO {
    private Integer page = 0;
    private Integer pageSize = 5;
    private String sortBy;
    private String sortDirection;
    private String searchTerm;
}


