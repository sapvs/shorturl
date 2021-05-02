FROM java:8
EXPOSE 8080

# Wait for it script to wait for cassandra ready [https://github.com/vishnubob/wait-for-it/blob/master/wait-for-it.sh]
COPY docker/tinyurl/wait-for-it.sh ./wait-for-it.sh
RUN chmod a+x wait-for-it.sh

ADD target/tinyurl-0.1.jar ./tinyurl.jar

# Entry pint takes care of cassandra coming up on port 9042,
ENTRYPOINT [ "./wait-for-it.sh", "-s", "-t", "100", "cassandra:9042",  "--", "java", "-jar", "tinyurl.jar" ]
