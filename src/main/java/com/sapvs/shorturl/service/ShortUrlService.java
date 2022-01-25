package com.sapvs.shorturl.service;

import com.sapvs.shorturl.model.ShortURLData;
import com.sapvs.shorturl.repository.ShortUrlCassandraRepository;
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
public class ShortUrlService {

    private static final Logger LOG = LoggerFactory.getLogger(ShortUrlService.class);

    @Value("${short.url.len}")
    private int shortIdLength;

    @Autowired
    ShortUrlCassandraRepository tinyURLCassandraRepository;

    @Cacheable(value = "shortToUrlCache")
    public String getLongURLForID(String id) {
        LOG.info("Searching for long URL against ID={}", id);
        Optional<ShortURLData> tinyURLOptional = tinyURLCassandraRepository.findById(id);
        if (tinyURLOptional.isPresent()) {
            LOG.info("long URL found={}", tinyURLOptional.get());
            return tinyURLOptional.get().getLongURL();
        }
        LOG.info("long URL not found");
        return null;
    }

    public List<ShortURLData> getAll() {
        LOG.info("Getting all URL mappings");
        return tinyURLCassandraRepository.findAll();
    }


    public ShortURLData createShortURLMapping(String longUrl) {
        LOG.info("Creating short URL mapping for {}", longUrl);
        ShortURLData tinyURLData = tinyURLCassandraRepository.save(ShortURLData.instance(shortIdLength, longUrl));
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
