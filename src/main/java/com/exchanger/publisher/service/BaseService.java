package com.exchanger.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public abstract class BaseService<T, K, R extends ListCrudRepository<T, K>> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Transactional(readOnly = true)
    public T findById(K id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean existsById(K id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<T> findAllById(Iterable<K> ids) {
        return repository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    public void deleteById(K id) {
        repository.deleteById(id);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends K> ids) {
        repository.deleteAllById(ids);
    }

    public void deleteAll(Iterable<T> entities) {
        repository.deleteAll(entities);
    }
}
