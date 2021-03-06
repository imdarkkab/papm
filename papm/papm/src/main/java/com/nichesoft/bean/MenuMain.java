/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.bean;

import java.io.Serializable;

import javax.validation.constraints.*;


public class MenuMain implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer menuMainId;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 250 )
    private String label;


    private Integer seq;


    private Integer messageId;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setMenuMainId( Integer menuMainId ) {
        this.menuMainId = menuMainId ;
    }

    public Integer getMenuMainId() {
        return this.menuMainId;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setLabel( String label ) {
        this.label = label;
    }
    public String getLabel() {
        return this.label;
    }

    public void setSeq( Integer seq ) {
        this.seq = seq;
    }
    public Integer getSeq() {
        return this.seq;
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
        sb.append(menuMainId);
        sb.append("|");
        sb.append(label);
        sb.append("|");
        sb.append(seq);
        sb.append("|");
        sb.append(messageId);
        return sb.toString(); 
    } 


}
