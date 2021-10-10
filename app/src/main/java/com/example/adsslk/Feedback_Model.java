package com.example.adsslk;

import org.w3c.dom.Comment;

import java.util.jar.Attributes;

public class Feedback_Model {
    String UserName,Name,Comment;
    Feedback_Model(){

   }
    public Feedback_Model(String userName, String name, String comment) {
        this.UserName = UserName;
        this.Name = Name;
        this.Comment = Comment;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
