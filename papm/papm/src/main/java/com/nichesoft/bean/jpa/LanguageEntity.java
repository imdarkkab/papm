/*
 * Created on 27 �.�. 2558 ( Time 16:44:11 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.nichesoft.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "language"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="language", catalog="papm" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="LanguageEntity.countAll", query="SELECT COUNT(x) FROM LanguageEntity x" )
} )
public class LanguageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="language_id", nullable=false)
    private Integer    languageId   ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="language", nullable=false, length=40)
    private String     language     ;

    @Column(name="short_name", length=40)
    private String     shortName    ;

    @Column(name="flag_img", nullable=false, length=256)
    private String     flagImg      ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="language", targetEntity=MessagesEntity.class)
    private List<MessagesEntity> listOfMessages;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public LanguageEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setLanguageId( Integer languageId ) {
        this.languageId = languageId ;
    }
    public Integer getLanguageId() {
        return this.languageId;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : language ( VARCHAR ) 
    public void setLanguage( String language ) {
        this.language = language;
    }
    public String getLanguage() {
        return this.language;
    }

    //--- DATABASE MAPPING : short_name ( VARCHAR ) 
    public void setShortName( String shortName ) {
        this.shortName = shortName;
    }
    public String getShortName() {
        return this.shortName;
    }

    //--- DATABASE MAPPING : flag_img ( VARCHAR ) 
    public void setFlagImg( String flagImg ) {
        this.flagImg = flagImg;
    }
    public String getFlagImg() {
        return this.flagImg;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfMessages( List<MessagesEntity> listOfMessages ) {
        this.listOfMessages = listOfMessages;
    }
    public List<MessagesEntity> getListOfMessages() {
        return this.listOfMessages;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(languageId);
        sb.append("]:"); 
        sb.append(language);
        sb.append("|");
        sb.append(shortName);
        sb.append("|");
        sb.append(flagImg);
        return sb.toString(); 
    } 

}
