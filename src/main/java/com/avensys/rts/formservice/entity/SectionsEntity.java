package com.avensys.rts.formservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "sections")
@Table( name = "sections")
@Data
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

    //More Fields
    @Column(name = "is_title", columnDefinition = "boolean default false")
    private Boolean isTitle;

}
