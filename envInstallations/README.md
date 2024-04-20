Vagrant
```
```

MiniKube
```
sudo apt update; sudo apt upgrade -y; sudo reboot; sudo apt install ca-certificates curl gnupg wget apt-transport-https -y; sudo install -m 0755 -d /etc/apt/keyrings; curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg; sudo chmod a+r /etc/apt/keyrings/docker.gpg; echo "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu"$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null; sudo apt update
```
```
```
MiniShift
```
wget https://github.com/minishift/minishift/releases/download/v1.34.1/minishift-1.34.1-linux-amd64.tgz; tar xf minishift-1.34.1-linux-amd64.tgz; export MINISHIFT_HOME=/mnt/images/minishift; minishift config set disk-size 32G; minishift config set memory 4096; minishift config set openshift-version v3.11.0; minishift start; oc login -u system:admin

minishift config view
```
Docker on Ubuntu
```
sudo apt update; sudo apt install apt-transport-https ca-certificates curl software-properties-common -y; curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg; echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null; sudo apt update; sudo apt install docker-ce -y
```
