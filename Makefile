image: package
	podman build -t tinyurl:latest docker/tinyurl

package: clean
	mvn package

clean:
	mvn clean

pod: package
	podman-compose -f docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

dock:
	sudo docker-compose -f docker/docker-compose.yml up --build --force-recreate --abort-on-container-exit

kill:
	podman-compose -f docker/docker-compose.yml down

