/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.bean;

import java.io.Serializable;

import javax.validation.constraints.*;


public class MenuSub implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer menuSubIs;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    

    private Integer menuMainId;

    @NotNull
    @Size( min = 1, max = 40 )
    private String label;

    @NotNull
    @Size( min = 1, max = 256 )
    private String link;


    private Integer parentId;


    private Integer seq;

    @NotNull
    private Boolean isActive;


    private Integer messageId;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setMenuSubIs( Integer menuSubIs ) {
        this.menuSubIs = menuSubIs ;
    }

    public Integer getMenuSubIs() {
        return this.menuSubIs;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setMenuMainId( Integer menuMainId ) {
        this.menuMainId = menuMainId;
    }
    public Integer getMenuMainId() {
        return this.menuMainId;
    }

    public void setLabel( String label ) {
        this.label = label;
    }
    public String getLabel() {
        return this.label;
    }

    public void setLink( String link ) {
        this.link = link;
    }
    public String getLink() {
        return this.link;
    }

    public void setParentId( Integer parentId ) {
        this.parentId = parentId;
    }
    public Integer getParentId() {
        return this.parentId;
    }

    public void setSeq( Integer seq ) {
        this.seq = seq;
    }
    public Integer getSeq() {
        return this.seq;
    }

    public void setIsActive( Boolean isActive ) {
        this.isActive = isActive;
    }
    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setMessageId( Integer messageId ) {
        this.messageId = messageId;
    }
    public Integer getMessageId() {
        return this.messageId;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(menuSubIs);
        sb.append("|");
        sb.append(menuMainId);
        sb.append("|");
        sb.append(label);
        sb.append("|");
        sb.append(link);
        sb.append("|");
        sb.append(parentId);
        sb.append("|");
        sb.append(seq);
        sb.append("|");
        sb.append(isActive);
        sb.append("|");
        sb.append(messageId);
        return sb.toString(); 
    } 


}
