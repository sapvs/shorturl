package com.test.tinyurl.controller;

import com.test.tinyurl.model.TinyURLData;
import com.test.tinyurl.service.TinyURLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.Objects.nonNull;


@RestController
@RequestMapping("tinyurl")
@Api(value = "API to CRUD shortened URLs with Redis Cache and Cassandra backend", produces = "application/json")
public class TinyURLRestController {

    @Autowired
    private TinyURLService tinyURLService;

    @ApiOperation(value = "For given short URL, redirect to Long URL", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 302, message = "Long URL found for short url"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @GetMapping("{id}")
    public ResponseEntity getLongURL(@PathVariable String id) {
        String redirectURL = tinyURLService.getLongURLForID(id);
        if (nonNull(redirectURL)) {
            return ResponseEntity.status(HttpStatus.FOUND).
                    location(URI.create(redirectURL)).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL mapping not found for " + id);
    }


    @ApiOperation(value = "Deletes URL mapping for given short url")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @DeleteMapping("{id}")
    public void deleteURLByID(@PathVariable String id) {
        tinyURLService.removeURLByID(id);
    }

    @ApiOperation(value = "Creates URL mapping and returns the short URL", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 404, message = "Short URL not found in database.")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TinyURLData> createShortURL(@RequestBody String longUrl) {
        return new ResponseEntity<TinyURLData>(tinyURLService.createShortURLMapping(longUrl.toString()), HttpStatus.CREATED);
    }
}
