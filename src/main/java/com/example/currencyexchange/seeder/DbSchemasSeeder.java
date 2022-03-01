package com.example.currencyexchange.seeder;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;


@Configuration
public class DbSchemasSeeder {

    @Value("${custom.db.seeder.mode}")
    private String seederMode;

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        if (SeederMode.NONE.name().toLowerCase().equals(seederMode)) return initializer;

        if (SeederMode.DROP.name().toLowerCase().equals(seederMode)
                || SeederMode.DROP_CREATE.name().toLowerCase().equals(seederMode)) {
            populator.addPopulators(
                    new ResourceDatabasePopulator(new ClassPathResource("db/drop.sql"))
            );
            initializer.setDatabasePopulator(populator);

        }

        if (SeederMode.CREATE.name().toLowerCase().equals(seederMode)
                || SeederMode.DROP_CREATE.name().toLowerCase().equals(seederMode)) {
            populator.addPopulators(
                    new ResourceDatabasePopulator(new ClassPathResource("db/schema.sql")),
                    new ResourceDatabasePopulator(new ClassPathResource("db/data.sql"))
            );

        }

        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    public enum SeederMode {
        CREATE, DROP, UPDATE, DROP_CREATE, NONE
    }
}

