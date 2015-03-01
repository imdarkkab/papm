/*
 * Created on 28 �.�. 2558 ( Time 11:57:03 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer userId;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    @Size( min = 1, max = 40 )
    private String userName;

    @NotNull
    @Size( min = 1, max = 40 )
    private String password;

    @NotNull
    private Date createdAt;

    @NotNull
    private Integer createdBy;


    private Integer employeeId;


    private Date updatedAt;


    private Integer updatedBy;

    @NotNull
    private Boolean active;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setUserId( Integer userId ) {
        this.userId = userId ;
    }

    public Integer getUserId() {
        return this.userId;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setUserName( String userName ) {
        this.userName = userName;
    }
    public String getUserName() {
        return this.userName;
    }

    public void setPassword( String password ) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
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

    public void setEmployeeId( Integer employeeId ) {
        this.employeeId = employeeId;
    }
    public Integer getEmployeeId() {
        return this.employeeId;
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

    public void setActive( Boolean active ) {
        this.active = active;
    }
    public Boolean getActive() {
        return this.active;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(userId);
        sb.append("|");
        sb.append(userName);
        sb.append("|");
        sb.append(password);
        sb.append("|");
        sb.append(createdAt);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(employeeId);
        sb.append("|");
        sb.append(updatedAt);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(active);
        return sb.toString(); 
    } 


}