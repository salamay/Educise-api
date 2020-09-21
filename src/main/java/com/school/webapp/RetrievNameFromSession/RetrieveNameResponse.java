package com.school.webapp.RetrievNameFromSession;

public class RetrieveNameResponse {
    public String name;

    public RetrieveNameResponse() {
    }

    public RetrieveNameResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
