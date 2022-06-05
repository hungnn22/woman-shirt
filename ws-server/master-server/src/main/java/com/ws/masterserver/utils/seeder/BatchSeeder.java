package com.ws.masterserver.utils.seeder;

import java.util.Arrays;

public class BatchSeeder implements Seeder {

    private final Seeder[] seeders = new Seeder[]{
            new UserSeeder(),
    };

    @Override
    public void seed() {
        Arrays.asList(seeders).forEach(Seeder::seed);
    }
}
