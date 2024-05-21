Vagrant
```
sudo apt install virtualbox -y; sudo apt install vagrant; wget https://releases.hashicorp.com/vagrant/2.3.7/vagrant_2.3.7-1_amd64.deb; sudo dpkg -i vagrant_2.3.7-1_amd64.deb; mkdir ~/project_name; mkdir ~/vagrant-ubuntu; cd ~/vagrant-ubuntu; vagrant init ubuntu/trusty64; vagrant up; vagrant ssh

vagrant halt
vagrant destroy
sudo apt remove vagrant
sudo apt autoremove vagrant
sudo apt autoremove --purge vagrant
```

MiniKube
```
sudo apt update; sudo apt upgrade -y; sudo reboot; sudo apt install ca-certificates curl gnupg wget apt-transport-https -y; sudo install -m 0755 -d /etc/apt/keyrings; curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg; sudo chmod a+r /etc/apt/keyrings/docker.gpg; echo "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu"$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null; sudo apt update
```
```
sudo apt update; //docker instalation // ; sudo swapoff -a; sudo firewall-cmd --permanent --add-port=6443/tcp; sudo firewall-cmd --permanent --add-port=2379-2380/tcp; sudo firewall-cmd --permanent --add-port=10250/tcp; sudo firewall-cmd --permanent --add-port=10251/tcp; sudo firewall-cmd --permanent --add-port=10252/tcp; sudo firewall-cmd --permanent --add-port=10255/tcp; sudo firewall-cmd –-reload; modprobe br_netfilter; sudo sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab

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
Terraform on Ubuntu
```
sudo apt-get install unzip -y; wget https://releases.hashicorp.com/terraform/1.8.0/terraform_1.8.0_linux_amd64.zip; unzip terraform_1.8.0_linux_amd64.zip; sudo mv terraform /usr/local/bin/; terraform --version
```
