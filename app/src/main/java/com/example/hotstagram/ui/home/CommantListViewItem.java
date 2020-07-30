package com.example.hotstagram.ui.home;

public class CommantListViewItem {

    private String profileuri;
    private String name;
    private String commant;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getCommant() { return commant; }
    public void setCommant(String commant) { this.commant = commant; }

    public String getProfileuri() { return profileuri; }
    public void setProfileuri(String profileuri) { this.profileuri = profileuri; }


}
