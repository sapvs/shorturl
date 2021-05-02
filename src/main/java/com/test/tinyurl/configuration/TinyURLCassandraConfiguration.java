package com.test.tinyurl.configuration;

import com.test.tinyurl.model.TinyURLData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableCassandraRepositories(basePackages = "com.test.tinyurl.model")
public class TinyURLCassandraConfiguration extends AbstractCassandraConfiguration {
    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }


    @Override
    public String[] getEntityBasePackages() {
        return new String[]{TinyURLData.class.getPackage().getName()};
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspaceName)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Arrays.asList(specification);
    }

    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(keyspaceName));
    }


    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE_DROP_UNUSED;
    }
}
