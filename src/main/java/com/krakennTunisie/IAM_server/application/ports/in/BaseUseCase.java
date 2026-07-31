package com.krakennTunisie.IAM_server.application.ports.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseUseCase<
        PAGE_ITEM,
        RESPONSE,
        CREATE,
        UPDATE,
        ID> {

    RESPONSE create(CREATE request);

    RESPONSE update(ID id, UPDATE request);

    RESPONSE get(ID id);

    Page<PAGE_ITEM> getAll(String keyword, String filter, int page, int size);

    void delete(ID id);
}
