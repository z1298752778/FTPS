package com.leateck.model.messageuserbean;

public class MessageUserBean {
    String userName;
    String description;
    Boolean isSelect;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MessageUserBin{" +
                "userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }
}
