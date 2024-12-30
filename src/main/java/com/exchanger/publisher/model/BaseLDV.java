package com.exchanger.publisher.model;

import com.exchanger.publisher.model.key.LDVID;
import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseLDV {

    @EmbeddedId
    private LDVID id;


    public BaseLDV() {
    }

    public BaseLDV(LDVID id) {
        this.id = id;
    }

    public LDVID getId() {
        return id;
    }

    public void setId(LDVID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseLDV baseLDV = (BaseLDV) o;
        return Objects.equals(id, baseLDV.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
