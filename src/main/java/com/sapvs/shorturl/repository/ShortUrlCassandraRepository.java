package com.sapvs.shorturl.repository;

import com.sapvs.shorturl.model.ShortURLData;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ShortUrlCassandraRepository extends CassandraRepository<ShortURLData, String> {
}
