package com.school.webapp.RetrieveParentInformation;

import java.util.List;

public class RetrieveParentInformationResponseEntity {
    private String fathername;
    private String mothername;
    private byte[] fatherpicture;
    private byte[] motherpictures;
    private List<String> associatechildnames;
    private List<byte[]> associatechildpictures;

    public RetrieveParentInformationResponseEntity() {
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public List<String> getAssociatechildnames() {
        return associatechildnames;
    }

    public void setAssociatechildnames(List<String> associatechildnames) {
        this.associatechildnames = associatechildnames;
    }

    public List<byte[]> getAssociatechildpictures() {
        return associatechildpictures;
    }

    public void setAssociatechildpictures(List<byte[]> associatechildpictures) {
        this.associatechildpictures = associatechildpictures;
    }

    public byte[] getFatherpicture() {
        return fatherpicture;
    }

    public void setFatherpicture(byte[] fatherpicture) {
        this.fatherpicture = fatherpicture;
    }

    public byte[] getMotherpictures() {
        return motherpictures;
    }

    public void setMotherpictures(byte[] motherpictures) {
        this.motherpictures = motherpictures;
    }
}
