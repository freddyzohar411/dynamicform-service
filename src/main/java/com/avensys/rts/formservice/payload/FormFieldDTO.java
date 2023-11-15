package com.avensys.rts.formservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormFieldDTO {
    private String label;
    private String name;
    private String placeholder;
    private Boolean required;
    private String requiredErrorMessage;
    private String pattern;
    private String patternValidationErrorMessage;
    private Integer minLength;
    private String minLengthErrorMessage;
    private Integer maxLength;
    private String maxLengthErrorMessage;
    private Boolean emailValidation;
    private String emailValidationErrorMessage;
    private Integer maxValue;
    private String maxValueErrorMessage;
    private Integer minValue;
    private String minValueErrorMessage;
    private String fileTypeValidation;
    private String fileTypeValidationErrorMessage;
    private Integer fileSizeValidation;
    private String fileSizeValidationErrorMessage;
    private String options;
    private String parent;
    private String visible;
    private String userGroup;
    private String tableConfig;
    private String tableSetting;
    private String countryOptions;
    private String type;
    private String fieldId;
    private String fieldType;
    private Boolean isUsed;
    private String buttonText;
    private String buttonName;
    private String buttonType;
    private String buttonClass;
    private String buttonLocation;
    private String subName;
    private String copyFields;
    private String fieldLocation;
    private String fieldSize;
    private String wordSize;
    private String wordText;

    //Added 15112023
    private String list;
}
