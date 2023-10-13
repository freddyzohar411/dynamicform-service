package com.avensys.rts.formservice.entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "formSubmissions")
@Table(name = "form_submissions")
public class FormSubmissionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "form_id")
    private Integer formId;

    @Column(name="submission_data", columnDefinition = "TEXT")
    private String submissionData;

//    @Column(name="submission_data", columnDefinition = "jsonb")
//    @Convert(converter = JsonbConverter.class)
//    private Object submissionData;

    @Column(name="entity_id")
    private Integer entityId;

    @Column(name="entity_type")
    private String entityType;

}
