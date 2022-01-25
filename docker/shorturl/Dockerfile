FROM sapvs/openjre-alpine:8-latest
EXPOSE 8080
ENV CASSANDRA_HOST=localhost
ENV CASSANDRA_PORT=9042

COPY --chown=java:java docker/shorturl/wait-for-it.sh ./wait-for-it.sh
RUN chmod a+x wait-for-it.sh

ADD target/shorturl-0.1.jar ./shorturl.jar

# Entry pint takes care of cassandra coming up on port 9042,
ENTRYPOINT exec ./wait-for-it.sh  $CASSANDRA_HOST:$CASSANDRA_PORT -t 100 -- java -jar shorturl.jar