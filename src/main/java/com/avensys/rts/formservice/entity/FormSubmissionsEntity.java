package com.avensys.rts.formservice.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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

	// Old Code
//    @Column(name="submission_data", columnDefinition = "TEXT")
//    private String submissionData;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "submission_data", columnDefinition = "jsonb")
	private JsonNode submissionData;

	@Column(name = "entity_id")
	private Integer entityId;

	@Column(name = "entity_type")
	private String entityType;

}
