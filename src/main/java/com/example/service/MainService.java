package com.example.service;

//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("rawtypes")
public abstract class MainService {

//    protected abstract UUID getId(T item);
//
//    protected abstract List<T> findAll();
//
//    protected abstract T save(T item);
//
//    protected abstract void deleteById(UUID id);
//
//    public List<T> getAll() {
//        return findAll();
//    }
//
//    public Optional<T> getById(UUID id) {
//        return findAll().stream()
//                .filter(item -> getId(item).equals(id))
//                .findFirst();
//    }
//
//    public T add(T item) {
//        if (getById(getId(item)).isPresent()) {
//            throw new RuntimeException("Item with ID '" + getId(item) + "' already exists.");
//        }
//        return save(item);
//    }
//
//    public void delete(UUID id) {
//        deleteById(id);
//    }
}