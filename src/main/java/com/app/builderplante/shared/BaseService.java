package com.app.builderplante.shared;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    public boolean existsById(ID id) {
        return getRepository().existsById(id);
    }
}
