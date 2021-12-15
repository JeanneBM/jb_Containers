FROM centos:9
MAINTAINER JeanneBM

WORKDIR /GIT

#RUN dnf install openssh-server sudo nano epel-release openssl certmonger; systemctl enable sshd.service -y

RUN dnf update -y
RUN dnf install git -y
RUN git --version

RUN mkdir Repo
RUN cd Repo
RUN git init

RUN mkdir -p /var/run/sshd ; chmod -rx /var/run/sshd
RUN touch /etc/ssh/sshd_config; chmod -rxw /etc/ssh/sshd_config
RUN ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key

RUN sed -ri 's/#PermitRootLogin yes/PermitRootLogin yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/#UsePAM no/UsePAM no/g' /etc/ssh/sshd_config

EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]
