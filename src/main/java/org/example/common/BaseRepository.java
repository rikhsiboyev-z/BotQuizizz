package org.example.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<ENTITY extends BaseEntity<ID>, ID> implements Repository<ENTITY, ID> {

    private List<ENTITY> entities = new ArrayList<>();

    @Override
    public void create(ENTITY entity) {

        entities.add(entity);
    }

    @Override
    public Optional<ENTITY> findById(ID id) {

        return entities.stream().
                filter(entity -> entity.getId().equals(id)).
                findFirst();


    }

    @Override
    public void delete(ID id) {
        entities.removeIf(entity -> entity.getId().equals(id));
    }

    @Override
    public List<ENTITY> getAll() {

        return entities;
    }
}
