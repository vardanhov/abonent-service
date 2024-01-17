package com.abonent.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "abonentid")
public class AbonentId implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "ctn")
    private String ctn;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    public AbonentId() {
    }

    public AbonentId(String ctn) {
        this.ctn = ctn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
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
