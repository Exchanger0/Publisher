package com.exchanger.publisher.model;

import com.exchanger.publisher.model.key.LDVID;
import jakarta.persistence.*;

@Entity
@Table(name = "dislike")
public class Dislike extends BaseLDV{
    public Dislike() {
    }

    public Dislike(LDVID id) {
        super(id);
    }
}
