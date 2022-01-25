package:
	mvn package

clean:
	mvn clean

up: package
	docker-compose --file docker/docker-compose.yml up --build 

down:
	docker-compose --file docker/docker-compose.yml down
