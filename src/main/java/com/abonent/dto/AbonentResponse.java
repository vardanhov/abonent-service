package com.abonent.dto;

public class AbonentResponse {
    private String ctn = "";
    private String abonentId = "";
    private String name = "";
    private String email = "";

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getAbonentId() {
        return abonentId;
    }

    public void setAbonentId(String abonentId) {
        this.abonentId = abonentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
