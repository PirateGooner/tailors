package com.project.android.tailor;

import java.util.ArrayList;

public class DesignsModel {

    private int designID;
    private String description;
    private String uploader;
    private int numberOfLikes;
    private String uri;
    private ArrayList<String> usersWhoLiked;

    public DesignsModel(){

    }

    public DesignsModel(int designID,String description,String uploader, int numberOfLikes, String uri, ArrayList<String> usersWhoLiked){
        this.designID=designID;
        this.description=description;
        this.uploader=uploader;
        this.numberOfLikes=numberOfLikes;
        this.uri=uri;
        this.usersWhoLiked=usersWhoLiked;
    }

    public int getDesignID() {
        return designID;
    }

    public String getDescription() {
        return description;
    }

    public String getUploader() {
        return uploader;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public String getUri() {
        return uri;
    }

    public ArrayList<String> getUsersWhoLiked() {
        return usersWhoLiked;
    }
}
