package com.ws.master_service.utils.seeder;

import javax.transaction.Transactional;

public interface Seeder {
    @Transactional
    void seed();
}
