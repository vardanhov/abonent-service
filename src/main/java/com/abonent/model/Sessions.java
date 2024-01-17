package com.abonent.model;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class Sessions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cellId")
    private String cellId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(formula = @JoinFormula(value = "ctn", referencedColumnName = "ctn")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "ctn", referencedColumnName = "ctn"))
    })

    private AbonentId ctn;

    public Sessions(String cellId) {
        this.cellId = cellId;
    }

    public Sessions(String cellId, AbonentId ctn) {
        this.cellId = cellId;
        this.ctn = ctn;
    }

    public Sessions() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public AbonentId getCtn() {
        return ctn;
    }

    public void setCtn(AbonentId ctn) {
        this.ctn = ctn;
    }
}
