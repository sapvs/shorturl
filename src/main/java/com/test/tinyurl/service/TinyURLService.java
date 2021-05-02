package com.test.tinyurl.service;

import com.test.tinyurl.model.TinyURLData;
import com.test.tinyurl.repository.TinyURLCassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyURLService {

    @Value("${short.url.len}")
    private int shortIdLength;

    @Autowired
    TinyURLCassandraRepository tinyURLCassandraRepository;

    @Cacheable(value = "shortToUrlCache")
    public String getLongURLForID(String id) {
        Optional<TinyURLData> tinyURLOptional = tinyURLCassandraRepository.findById(id);
        if (tinyURLOptional.isPresent()) {
            return tinyURLOptional.get().getLongURL();
        }
        return null;
    }

    public TinyURLData createShortURLMapping(String longUrl) {
       return tinyURLCassandraRepository.save(TinyURLData.instance(shortIdLength, longUrl));
    }

    @CacheEvict(value = "shortToUrlCache")
    public void removeURLByID(String id) {
        tinyURLCassandraRepository.deleteById(id);
    }

}
