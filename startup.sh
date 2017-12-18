#!/bin/sh

/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &

sleep 10

cd /opt/jboss/MyHotel-BackEnd

mvn wildfly:deploy

while(true){
	sleep 99999
}
