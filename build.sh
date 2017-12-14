echo Build du Front

cd MyHotel-FrontEnd && ng build
cd ..

echo Build Docker

docker build . -t wildfly-config -f Dockerbase

docker build . -t wildfly-app

docker run --hostname wildfly-app --name wildfly-app -p 8080:8080 -p 9990:9990 -d wildfly-app
