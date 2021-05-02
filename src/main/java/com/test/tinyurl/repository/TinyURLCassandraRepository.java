package com.test.tinyurl.repository;

import com.test.tinyurl.model.TinyURLData;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TinyURLCassandraRepository extends CassandraRepository<TinyURLData, String> {
}
