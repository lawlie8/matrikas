FROM ubuntu:22.04
#Update ubuntu package list
RUN apt-get update \
&& apt-get upgrade -y \
&& apt-get dist-upgrade -y \
&& apt install systemd -y
#Change Working dir to /home
WORKDIR /home
#Install git
RUN apt install git -y \
&& apt install wget -y \
&& apt install tar -y
#Download maven 3.9.12 and jdk17.0.1
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.12/binaries/apache-maven-3.9.12-bin.tar.gz
RUN tar xzvf apache-maven-3.9.12-bin.tar.gz
RUN wget https://download.java.net/java/GA/jdk17.0.1/2a2082e5a09d4267845be086888add4f/12/GPL/openjdk-17.0.1_linux-x64_bin.tar.gz
RUN tar xzvf openjdk-17.0.1_linux-x64_bin.tar.gz
RUN export PATH=$PATH:/home/jdk-17.0.1/bin
RUN export PATH=$PATH:/home/apache-maven-3.9.12/bin
#Download mariadb and create database
RUN apt install mariadb-server -y
#Clone git
RUN git clone https://github.com/lawlie8/matrikas.git
WORKDIR /home/matrikas
#RUN mvn clean install -DskipTests
