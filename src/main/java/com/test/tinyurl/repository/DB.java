package com.test.tinyurl.repository;

public interface DB {
    String KEY_SPACE = "tinyurl";
    String TABLE_NAME = "urldata";

    String CREATE_KEYSPACE = "CREATE KEYSPACE IF NOT EXISTS " + KEY_SPACE +
            " WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'}  AND durable_writes = true";
    String USE_TINYURL = "USE " + KEY_SPACE;
    String CREATE_TABLE_URL_DATA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "(id                          text, "
            + "url                         text, "
            + "PRIMARY KEY (id)) WITH "
            + "bloom_filter_fp_chance=0.001000 AND "
            + "caching={'keys': 'ALL' } AND "
            + "comment='' AND "
            + "gc_grace_seconds=10800 AND "
            + "default_time_to_live=0 AND "
            + "speculative_retry='99.0PERCENTILE' AND "
            + "memtable_flush_period_in_ms=0 AND "
            + "compaction={'class': 'LeveledCompactionStrategy'} AND "
            + "compression={'sstable_compression': 'LZ4Compressor'}";

}
