# salvador-hq

# update maven

sudo apt-get remove maven2
sudo apt-get install maven
export MAVEN_OPTS="-Xmx256m"

# install java
https://gist.github.com/naxmefy/c9b49d10de6827a336f81e6cbcccabe3

sudo echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get update -y
sudo apt-get install -y oracle-java8-installer
sudo rm -rf /var/lib/apt/lists/*
sudo rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable and update PATH
echo 'export JAVA_HOME=/usr/lib/jvm/java-8-oracle' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc 

source ~/.bashrc

#User Module
AngularJS
Bower
ESLint
Karma 
Jasmine
Karma-Jasmine Plugin
PhantomJS

Protractor
Grunt