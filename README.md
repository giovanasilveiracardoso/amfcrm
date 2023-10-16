git clone https://github.com/giovanasilveiracardoso/amfcrm.git

mvn clean

mvn package -Pproduction

docker-compose up
