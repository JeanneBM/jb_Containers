Docker CE on CentOS:

sudo yum install -y yum-utils; sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo; sudo yum install docker-ce docker-ce-cli containerd.io; yum list docker-ce --showduplicates | sort -r; sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io; sudo systemctl start docker; sudo docker run hello-world

Jenkins:

sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo; sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key; sudo yum upgrade; sudo yum install epel-release java-11-openjdk-devel; sudo yum install jenkins; sudo systemctl daemon-reload

https://kot-zrodlowy.pl/



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



docker system prune --all --force --volumes
