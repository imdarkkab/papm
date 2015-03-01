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
 * Persistent class for entity stored in table "user_permission"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="user_permission", catalog="papm" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="UserPermissionEntity.countAll", query="SELECT COUNT(x) FROM UserPermissionEntity x" )
} )
public class UserPermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_permission_id", nullable=false)
    private Integer    userPermissionId ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
	// "userId" (column "user_id") is not defined by itself because used as FK in a link 
	// "permissionId" (column "permission_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="permission_id", referencedColumnName="permission_id")
    private PermissionEntity permission  ;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id")
    private UserEntity user        ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public UserPermissionEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setUserPermissionId( Integer userPermissionId ) {
        this.userPermissionId = userPermissionId ;
    }
    public Integer getUserPermissionId() {
        return this.userPermissionId;
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

    public void setUser( UserEntity user ) {
        this.user = user;
    }
    public UserEntity getUser() {
        return this.user;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(userPermissionId);
        sb.append("]:"); 
        return sb.toString(); 
    } 

}