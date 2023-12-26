package com.avensys.rts.formservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "formFields")
@Table(name = "form_fields")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn( name="form_id", referencedColumnName = "id")
    private FormsEntity formsEntity;

    @Column(name="label")
    private String label;

    @Column(name="type")
    private String type;

    @Column(name="placeholder")
    private String placeholder;

    @Column(name="is_active")
    private Boolean isActive;

    @Column(name="regex")
    private String regex;

    @Column(name="min_length")
    private Integer minLength;

    @Column(name="max_length")
    private Integer maxLength;

    @Column(name="is_required")
    private Boolean isRequired;

    @Column(name="help")
    private String help;

    @Column(name="custom_attributes")
    private String customAttributes;

    @ManyToOne
    @JoinColumn( name="section_id", referencedColumnName = "id")
    private SectionsEntity sectionsEntity;

    @Column(name="field_name")
    private String fieldName;

    @Column(name="is_disabled")
    private Boolean isDisabled;

    // More
    @Column(name= "column_order")
    private Integer columnOrder;

    @Column (name = "column_number")
    private Integer columnNumber;

    @Column (name = "field_id")
    private String fieldId;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "is_used")
    private Boolean isUsed;

    // Button
    @Column (name = "button_text")
    private String buttonText;

    @Column(name = "button_type")
    private String buttonType;

    @Column(name = "button_name")
    private String buttonName;

    @Column(name = "button_class")
    private String buttonClass;

    @Column(name= "button_location")
    private String buttonLocation;

    // More fields 2
    @Column(name = "required_error_message")
    private String requiredErrorMessage;

    @Column(name = "regex_error_message")
    private String regexErrorMessage;

    @Column(name = "min_length_error_message")
    private String minLengthErrorMessage;

    @Column(name = "max_length_error_message")
    private String maxLengthErrorMessage;

    @Column(name ="email_validation")
    private Boolean emailValidation;

    @Column(name = "email_validation_error_message")
    private String emailValidationErrorMessage;

    @Column(name = "max_value")
    private Integer maxValue;

    @Column(name = "max_value_error_message")
    private String maxValueErrorMessage;

    @Column(name = "min_value")
    private Integer minValue;

    @Column(name = "min_value_error_message")
    private String minValueErrorMessage;

    @Column(name = "file_type_validation")
    private String fileTypeValidation;

    @Column(name = "file_type_validation_error_message")
    private String fileTypeValidationErrorMessage;

    @Column(name = "file_size_validation")
    private Integer fileSizeValidation;

    @Column(name = "file_size_validation_error_message")
    private String fileSizeValidationErrorMessage;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "parent")
    private String parent;

    @Column(name = "visible", columnDefinition = "TEXT")
    private String visible;

    @Column(name = "user_group" , columnDefinition = "TEXT")
    private String userGroup;

    @Column(name = "table_config", columnDefinition = "TEXT")
    private String tableConfig;

    @Column(name ="table_setting", columnDefinition = "TEXT")
    private String tableSetting;

    @Column(name="country_options")
    private String countryOptions;

    @Column(name="sub_name")
    private String subName;

    @Column(name="copy_fields")
    private String copyFields;

    @Column(name="field_location")
    private String fieldLocation;

    @Column(name="field_size")
    private String fieldSize;

    @Column(name="word_size")
    private String wordSize;

    @Column(name="word_text")
    private String wordText;

    // Added 15112023
    @Column(name="list")
    private String list;

    // Added 16112023
    @Column(name = "condition_validation", columnDefinition = "TEXT")
    private String conditionValidation;

    @Column(name = "condition_validation_error_message")
    private String conditionValidationErrorMessage;

    // Added 26122023
    @Column (name = "form_category_select")
    private String formCategorySelect;

    @Column (name = "information")
    private Boolean information;

    @Column (name = "information_text")
    private String informationText;

    // Unused fields
    @Column(name = "table_delete")
    private String tableDelete;

    @Column(name = "table_edit")
    private String tableEdit;

    @Column(name = "table_api")
    private String tableApi;

    @Column(name = "table_api_url")
    private String tableApiUrl;

}
