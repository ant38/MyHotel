#!/bin/sh

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo Start server wildfly

/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 &

echo Wait server wildfly

#wait_for_server
sleep 10

echo Deploy on server wildfly

cd /opt/jboss/MyHotel-BackEnd
#apt-get install maven
mvn wildfly:deploy

sleep 99999