Sapvas

1. Added nginx for scaling the tinyurl app
   1. why not scale cassandra, that is not our respo, our resp is our app
1. Removed cassandra password and multiple cassandra instances.
1. Map use in case of cache is not so good approach 
    1. it will grow in size, 
    2. how it is available in case of multiple instances is doubt.
    

for packaging and building docker image:

```mvn clean package && docker build -t tinyurl .```

for up/down

```docker-compose -f src/main/conf/docker-compose.yml up/down```

For scale:

```docker-compose -f src/main/conf/docker-compose.yml up --scale cassandra2=2```

URL to access

```http://localhost:8080/swagger-ui.html```

[Additional]

For node-tool

```docker exec -it <> nodetool -u cassandra -pw cassandra status```

For schema creation(alternative):

```
docker cp src/main/conf/schema.cql edfdac47b810:/opt/
docker exec -it cinst1 cqlsh -f /opt/schema.cql
```


