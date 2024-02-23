package com.avensys.rts.formservice.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormSchemaDTO {
	private Boolean isTitle;
	private String title;
	private Integer rowId;
	private List<DroppableZone> droppableZones;
}
