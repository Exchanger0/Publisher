package com.exchanger.publisher.model;

import com.exchanger.publisher.model.key.LDVID;
import jakarta.persistence.*;

@Entity
@Table(name = "views")
public class Views extends BaseLDV{
    public Views() {
    }

    public Views(LDVID id) {
        super(id);
    }
}
