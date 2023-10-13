package com.avensys.rts.formservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormSchemaDTO {
    private Boolean isTitle;
    private String title;
    private Integer rowId;
    private List<DroppableZone> droppableZones;
}
