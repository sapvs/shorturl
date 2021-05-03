package: clean
	mvn package

clean:
	mvn clean

up: package
	docker-compose --file docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

down:
	docker-compose --file docker/docker-compose.yml down

podup: package
	podman-compose --file docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

poddown:
	podman-compose --file docker/docker-compose.yml down
