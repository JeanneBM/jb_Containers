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
