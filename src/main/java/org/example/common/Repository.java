package org.example.common;

import java.awt.desktop.OpenFilesEvent;
import java.util.List;
import java.util.Optional;

public interface Repository<ENTITY extends BaseEntity<ID>, ID> {


    void create(ENTITY entity);

    Optional<ENTITY> findById(ID id);

    void delete(ID id);

    List<ENTITY> getAll();

}
