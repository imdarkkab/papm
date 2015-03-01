/*
 * Created on 27 �.�. 2558 ( Time 16:44:31 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Corporate implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer corporateId;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    @Size( min = 1, max = 40 )
    private String corporateName;

    @NotNull
    private Date createdAt;

    @NotNull
    private Integer createdBy;


    private Date updatedAt;


    private Integer updatedBy;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setCorporateId( Integer corporateId ) {
        this.corporateId = corporateId ;
    }

    public Integer getCorporateId() {
        return this.corporateId;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setCorporateName( String corporateName ) {
        this.corporateName = corporateName;
    }
    public String getCorporateName() {
        return this.corporateName;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedBy( Integer createdBy ) {
        this.createdBy = createdBy;
    }
    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setUpdatedAt( Date updatedAt ) {
        this.updatedAt = updatedAt;
    }
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedBy( Integer updatedBy ) {
        this.updatedBy = updatedBy;
    }
    public Integer getUpdatedBy() {
        return this.updatedBy;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(corporateId);
        sb.append("|");
        sb.append(corporateName);
        sb.append("|");
        sb.append(createdAt);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(updatedAt);
        sb.append("|");
        sb.append(updatedBy);
        return sb.toString(); 
    } 


}
