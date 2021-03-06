/*
 * Created on 27 �.�. 2558 ( Time 16:44:11 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.nichesoft.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;


import javax.persistence.*;

/**
 * Persistent class for entity stored in table "role_permission"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="role_permission", catalog="papm" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="RolePermissionEntity.countAll", query="SELECT COUNT(x) FROM RolePermissionEntity x" )
} )
public class RolePermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="role_permission_id", nullable=false)
    private Integer    rolePermissionId ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
	// "roleId" (column "role_id") is not defined by itself because used as FK in a link 
	// "permissionId" (column "permission_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="permission_id", referencedColumnName="permission_id")
    private PermissionEntity permission  ;

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName="role_id")
    private RoleEntity role        ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public RolePermissionEntity() {
		super();
    }
    
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

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setPermission( PermissionEntity permission ) {
        this.permission = permission;
    }
    public PermissionEntity getPermission() {
        return this.permission;
    }

    public void setRole( RoleEntity role ) {
        this.role = role;
    }
    public RoleEntity getRole() {
        return this.role;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(rolePermissionId);
        sb.append("]:"); 
        return sb.toString(); 
    } 

}
