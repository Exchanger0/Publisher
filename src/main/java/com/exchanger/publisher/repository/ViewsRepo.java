package com.exchanger.publisher.repository;

import com.exchanger.publisher.model.Views;
import com.exchanger.publisher.model.key.LDVID;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewsRepo extends ListCrudRepository<Views, LDVID> {
}
