package: clean
	mvn package

clean:
	mvn clean

up:
	sudo docker-compose --file docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

down:
	sudo docker-compose --file docker/docker-compose.yml down

podup:
	podman-compose --file docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

poddown:
	podman-compose --file docker/docker-compose.yml down
