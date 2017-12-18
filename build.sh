echo Build du Front

cd MyHotel-FrontEnd && ng build
cd ..

echo Build Docker

docker build . -t wildfly-config -f Dockerbase

docker build . -t myhotel

docker run --hostname myhotel --name myhotel -p 8080:8080 -p 9990:9990 -d myhotel
