package com.app.builderplante.shared;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T extends BaseEntity, ID> {

    protected abstract BaseService<T, ID> getService();

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(getService().findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        return getService().findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        if (!getService().existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
