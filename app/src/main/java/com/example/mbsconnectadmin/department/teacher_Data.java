package com.example.mbsconnectadmin.department;

public class teacher_Data {
    private String name,mail,post,image,key;

    public teacher_Data() {
    }

    public teacher_Data(String name, String mail, String post,String image, String key) {
        this.name = name;
        this.mail = mail;
        this.post=post;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
