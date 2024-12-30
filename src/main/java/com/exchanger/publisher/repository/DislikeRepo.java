package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Dislike;
import com.exchanger.publisher.model.key.LDVID;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikeRepo extends ListCrudRepository<Dislike, LDVID> {
}
