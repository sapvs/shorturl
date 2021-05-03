package com.test.tinyurl.service;

import com.test.tinyurl.model.TinyURLData;
import com.test.tinyurl.repository.TinyURLCassandraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TinyURLService {

    private static final Logger LOG = LoggerFactory.getLogger(TinyURLService.class);

    @Value("${short.url.len}")
    private int shortIdLength;

    @Autowired
    TinyURLCassandraRepository tinyURLCassandraRepository;

    @Cacheable(value = "shortToUrlCache")
    public String getLongURLForID(String id) {
        LOG.info("Searching for long URL against ID={}", id);
        Optional<TinyURLData> tinyURLOptional = tinyURLCassandraRepository.findById(id);
        if (tinyURLOptional.isPresent()) {
            LOG.info("long URL found={}", tinyURLOptional.get());
            return tinyURLOptional.get().getLongURL();
        }
        LOG.info("long URL not found");
        return null;
    }

    public List<TinyURLData> getAll() {
        LOG.info("Getting all URL mappings");
        return tinyURLCassandraRepository.findAll();
    }


    public TinyURLData createShortURLMapping(String longUrl) {
        LOG.info("Creating short URL mapping for {}", longUrl);
        TinyURLData tinyURLData = tinyURLCassandraRepository.save(TinyURLData.instance(shortIdLength, longUrl));
        LOG.info("Created short URL mapping {}", tinyURLData);
        return tinyURLData;
    }

    @CacheEvict(value = "shortToUrlCache")
    public void removeURLByID(String id) {
        LOG.info("Removing short URL mapping for {}", id);
        tinyURLCassandraRepository.deleteById(id);
        LOG.info("Deleted successfully {}", id);
    }

}
