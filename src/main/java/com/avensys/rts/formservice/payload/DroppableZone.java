package com.avensys.rts.formservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroppableZone {
    private String id;
    private List<String> fieldIds;
}
