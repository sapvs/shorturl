FROM java:8
EXPOSE 8080
ENV CASSANDRA_HOST=localhost
ENV CASSANDRA_PORT=9042

# Wait for it script to wait for cassandra ready [https://github.com/vishnubob/wait-for-it/blob/master/wait-for-it.sh]
COPY docker/tinyurl/wait-for-it.sh ./wait-for-it.sh
RUN chmod a+x wait-for-it.sh

ADD target/tinyurl-0.1.jar ./tinyurl.jar

# Entry pint takes care of cassandra coming up on port 9042,
#ENTRYPOINT [ "./wait-for-it.sh", "-s", "-t", "2", "-h", "echo $CASSANDRA_HOST", "-p", "echo $CASSANDRA_PORT", "--", "java", "-jar", "tinyurl.jar" ]
ENTRYPOINT exec ./wait-for-it.sh -s -t 100 -h $CASSANDRA_HOST -p $CASSANDRA_PORT -- java -jar tinyurl.jar

