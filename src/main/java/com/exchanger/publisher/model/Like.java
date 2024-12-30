package com.exchanger.publisher.model;

import com.exchanger.publisher.model.key.LDVID;
import jakarta.persistence.*;

@Entity
@Table(name = "\"like\"")
public class Like extends BaseLDV{
    public Like() {
    }

    public Like(LDVID id) {
        super(id);
    }
}
