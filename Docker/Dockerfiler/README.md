docker build -t git .

docker create git

docker start 

winpty docker run -it git bash



#Uprawnianie docker'a: 

sudo groupadd docker 

sudo usermod -aG docker $USER 

Restart 


https://devhints.io/docker-compose

878d1b78a162   jenkins/jenkins                              "/sbin/tini -- /usr/…"   16 seconds ago   Up 9 seconds    0.0.0.0:8080->8080/tcp, 0.0.0.0:50000->50000/tcp   Jenkins
4fd4c889add3   selenium/standalone-chrome:4.1.0-20211209    "/opt/bin/entry_poin…"   7 minutes ago    Up 6 minutes    0.0.0.0:4444->4444/tcp, 5900/tcp                   selenium_node_chrom
5b2a00b14366   selenium/standalone-firefox:4.1.0-20211209   "/opt/bin/entry_poin…"   15 minutes ago   Up 15 minutes   4444/tcp, 0.0.0.0:4442->4442/tcp, 5900/tcp         selenium_node_firefox
5c2b51e12163   selenium/hub:4.1.0-20211209                  "/opt/bin/entry_poin…"   25 minutes ago   Up 25 minutes   0.0.0.0:4441->4441/tcp, 4442-4444/tcp              selenium_hub
021ab64e482b   sonatype/nexus3                              "sh -c ${SONATYPE_DI…"   32 minutes ago   Up 32 minutes   0.0.0.0:8081->8081/tcp                             nexus
fdd625cb359c   tomcat                                       "catalina.sh run"        45 minutes ago   Up 45 minutes   0.0.0.0:8888->8080/tcp                             tomcat
73d2ce1f50a6   sonarqube                                    "/opt/sonarqube/bin/…"   54 minutes ago   Up 54 minutes   0.0.0.0:9000->9000/tcp                             sonar
