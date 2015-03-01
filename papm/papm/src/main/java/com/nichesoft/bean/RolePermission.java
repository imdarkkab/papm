/*
 * Created on 27 �.�. 2558 ( Time 16:44:33 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.bean;

import java.io.Serializable;

import javax.validation.constraints.*;


public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer rolePermissionId;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    private Integer roleId;

    @NotNull
    private Integer permissionId;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setRolePermissionId( Integer rolePermissionId ) {
        this.rolePermissionId = rolePermissionId ;
    }

    public Integer getRolePermissionId() {
        return this.rolePermissionId;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setRoleId( Integer roleId ) {
        this.roleId = roleId;
    }
    public Integer getRoleId() {
        return this.roleId;
    }

    public void setPermissionId( Integer permissionId ) {
        this.permissionId = permissionId;
    }
    public Integer getPermissionId() {
        return this.permissionId;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(rolePermissionId);
        sb.append("|");
        sb.append(roleId);
        sb.append("|");
        sb.append(permissionId);
        return sb.toString(); 
    } 


}