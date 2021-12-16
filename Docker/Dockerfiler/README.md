docker build -t git .

docker create git

docker start 

winpty docker run -it git bash



#Uprawnianie docker'a: 

sudo groupadd docker 

sudo usermod -aG docker $USER 

Restart 


https://devhints.io/docker-compose



Start tutorial:
docker run -d -p 80:80 docker/getting-started


#error during connect: This error may indicate that the docker daemon is not running.: Get "http://%2F%2F.%2Fpipe%2Fdocker_engine/v1.24/containers/json": open //./pipe/docker_engine: The system cannot find the file specified.


"C:\Program Files\Docker\Docker\DockerCli.exe" -SwitchDaemon
