package com.sapvs.shorturl.controller;

import com.sapvs.shorturl.model.ShortURLData;
import com.sapvs.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.Objects.nonNull;


@RestController
@RequestMapping("shorturl")
@Api(value = "API to CRUD shortened URLs with Redis Cache and Cassandra backend", produces = "application/json")
public class ShortUrlRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ShortUrlRestController.class);

    @Autowired
    private ShortUrlService tinyURLService;
    private ShortURLData shortURLMapping;

    @ApiOperation(value = "For given short URL, redirect to Long URL", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 302, message = "Long URL found for short url"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @GetMapping("{id}")
    public ResponseEntity getLongURL(@PathVariable String id) {
        LOG.info("Got short id {}", id);
        String redirectURL = tinyURLService.getLongURLForID(id);
        if (nonNull(redirectURL)) {
            LOG.info("Found redirect URL  {}", redirectURL);
            return ResponseEntity.status(HttpStatus.FOUND).
                    location(URI.create(redirectURL)).build();
        }
        LOG.info("Redicrect URL not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL mapping not found for " + id);
    }


    @ApiOperation(value = "For given short URL, redirect to Long URL", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 302, message = "Long URL found for short url"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @GetMapping
    public  ResponseEntity<List<ShortURLData>> getAll() {
        LOG.info("Getting all ");
        List<ShortURLData> all = tinyURLService.getAll();
        LOG.debug("Got all  {}", all);
        return ResponseEntity.status(HttpStatus.OK).
                body(all);
    }


    @ApiOperation(value = "Deletes URL mapping for given short url")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @DeleteMapping("{id}")
    public void deleteURLByID(@PathVariable String id) {
        LOG.info("Got short id for delete {}", id);
        tinyURLService.removeURLByID(id);
        LOG.info("Deleted short ID mapping {}", id);
    }

    @ApiOperation(value = "Creates URL mapping and returns the short URL", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ShortURLData> createShortURL(@RequestBody String longUrl) {
        LOG.info("Creating short mapping for {}", longUrl);
        shortURLMapping = tinyURLService.createShortURLMapping(longUrl);
        LOG.info("Created short mapping {}", shortURLMapping);
        return new ResponseEntity<ShortURLData>(shortURLMapping, HttpStatus.CREATED);
    }
}
