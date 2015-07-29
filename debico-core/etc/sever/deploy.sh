sudo service tomcat7 stop
# backup mysql /home/ec2-user/bkp/
cd /home/ec2-user/git/debico
git pull
passphrase maisdomesmo
mvn clean install -Dmaven.test.skip=true
passphrase maisdomesmo
sudo rm -Rf /usr/share/tomcat7/logs/*
sudo rm -Rf /usr/share/tomcat7/work/*
sudo rm -Rf /usr/share/tomcat7/temp/*
sudo mv /usr/share/tomcat7/webapps/app.war /home/ec2-user/tmp/app-2.0.3.war
sudo rm -Rf /usr/share/tomcat7/webapps/*
sudo cp /home/ec2-user/git/debico/debico-ui/target/app.war /usr/share/tomcat7/webapps/ 
sudo service tomcat7 start
