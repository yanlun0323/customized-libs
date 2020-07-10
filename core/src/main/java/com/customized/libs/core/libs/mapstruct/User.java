package com.customized.libs.core.libs.mapstruct;

public class User {

    private Integer index;

    private String userName;

    private Integer userAge;


    public User(String userName, Integer userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }
}
