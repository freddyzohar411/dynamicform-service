package com.avensys.rts.formservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/***
 *
 * @author Koh He Xiang
 * This is the entity class for the account table in the database
 *
 */

@Entity(name = "forms")
@Table(name = "forms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id", length = 50)
    private Integer userId;

    @Column(name = "form_name", length = 50)
    private String formName;

    @Column(name = "is_active", length = 10, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "entity_type", length = 50)
    private String entityType;

    @Column(name = "is_deleted", length = 10, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Column(name = "created_by")
    private Integer createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // To section Entity
    @OneToMany(mappedBy = "formsEntity", cascade = CascadeType.ALL)
    private List<SectionsEntity> sectionsEntityList;

    // To form fields Entity
    @OneToMany(mappedBy = "formsEntity", cascade = CascadeType.ALL)
    private List<FormFieldsEntity> formFieldsEntityList;

    // More fields

    @Column (name = "form_type")
    private String formType;

    @Column(name = "stepper_number")
    private Integer stepperNumber;

    @ManyToOne
    @JoinColumn(name = "baseform", referencedColumnName = "id")
    private FormsEntity baseForm;

}
