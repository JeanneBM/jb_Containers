docker build -t git .
docker create git
docker start 
winpty docker run -it git bash

