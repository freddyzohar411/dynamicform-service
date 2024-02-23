package com.avensys.rts.formservice.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "sections")
@Table(name = "sections")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SectionsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "section_order")
	private Integer sectionOrder;

	@Column(name = "no_of_column")
	private Integer noOfColumn;

	// To form Entity
	@ManyToOne
	@JoinColumn(name = "form_id", referencedColumnName = "id")
	private FormsEntity formsEntity;

	// To formFields Entity
	@OneToMany(mappedBy = "sectionsEntity", cascade = CascadeType.ALL)
	private List<FormFieldsEntity> formFieldsEntityList;

	// More Fields
	@Column(name = "is_title", columnDefinition = "boolean default false")
	private Boolean isTitle;

}
