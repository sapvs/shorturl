image: package
	podman build -t tinyurl:latest docker/tinyurl

package: clean
	mvn package

clean:
	mvn clean

p:
	podman-compose -f docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

d:
	sudo docker-compose -f docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

k:
	sudo docker-compose -f docker/docker-compose.yml down
	podman-compose -f docker/docker-compose.yml down

