# Tiny URL 
## Architecture
1. nginx frontend
1. Spring Boot Backend
   1. Spring Starter Web
   1. Spring Starter Redis
   1. Spring Starter Data Cassandra
1. Redis Cache
1. Cassandra Database 


## Build
### Compile and package 
```mvn clean package```

### Docker Compose
From this folder

```docker-compose up -f docker/docker-compose.yml```

### Using Makefile

```make up```

### Scale 
```docker-compose -f docker/docker-compose.yml up --scale shorturl=2```

### Stop
From this folder

```docker-compose down -f docker/docker-compose.yml```

### Using Makefile

```make down```


## Testing

Service runs behind nginx proxying to spring boot container shorturl

### Swagger 
Access http://localhost:4000/swagger-ui.html

### Curl 
#### Create short URL for long URL 
```
curl -X POST "http://localhost:4000/shorturl" \
-H  "accept: application/json" \
-H  "Content-Type: application/json" \
-d "http://www.google.com"
```

Response 
```
{"id":"ailjoN","longURL":"http://www.google.com"}
```

#### Access short URL to confirm redirect in browser

```http://localhost:4000/shorturl/ailjoN```

### Delete mapping 

```curl -X DELETE http://localhost:4000/shorturl/ailjoN```


Confirm again on the browser, accessing URL should return 404.
