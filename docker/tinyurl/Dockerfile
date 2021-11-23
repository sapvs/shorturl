FROM sapvs/openjre-alpine:11-3.14
EXPOSE 8080
ENV CASSANDRA_HOST=localhost
ENV CASSANDRA_PORT=9042

# Wait for it script to wait for cassandra ready [https://github.com/vishnubob/wait-for-it/blob/master/wait-for-it.sh]
COPY --chown=java:java docker/tinyurl/wait-for-it.sh ./wait-for-it.sh
RUN chmod a+x wait-for-it.sh

ADD target/tinyurl-0.1.jar ./tinyurl.jar

# Entry pint takes care of cassandra coming up on port 9042,
ENTRYPOINT exec ./wait-for-it.sh  $CASSANDRA_HOST:$CASSANDRA_PORT -t 100 -- java -jar tinyurl.jar

