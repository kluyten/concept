package com.atos.concept.persistence;

// Generated May 20, 2015 3:43:45 PM by Hibernate Tools 4.3.1
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Slides generated by hbm2java
 */
@Entity
@Table(name = "slides")
public class Slides implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private int id_project;
    private String slideName;
    private String slideText;
    private Date creation;
    

    public Slides() {
    }

    public Slides(int id_project, String slideName) {
        this.id_project = id_project;
        this.slideName = slideName;
    }

    public Slides(int id_project, String slideName, String slideText, Date creation) {
        this.id_project = id_project;
        this.slideName = slideName;
        this.slideText = slideText;
        this.creation = creation;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProjectId(int id_project) {
        this.id_project = id_project;
    }

    @Column(name = "id_project", nullable = false)
    public int getProjectId() {
        return this.id_project;
    }

    @Column(name = "slide_name", nullable = false, length = 30)
    public String getSlideName() {
        return this.slideName;
    }

    public void setSlideName(String slideName) {
        this.slideName = slideName;
    }

    @Column(name = "slide_text", length = 65535)
    public String getSlideText() {
        return this.slideText;
    }

    public void setSlideText(String slideText) {
        this.slideText = slideText;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation", length = 19)
    public Date getCreation() {
        return this.creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
