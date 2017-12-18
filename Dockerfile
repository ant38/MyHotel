# Use latest jboss/base-jdk:7 image as the base
FROM wildfly-config

USER root
# Set the WILDFLY_VERSION env variable
#ENV WILDFLY_VERSION 9.0.1.Final
#ENV WILDFLY_SHA1 abe037d5d1cb97b4d07fbfe59b6a1345a39a9ae5
#ENV JBOSS_HOME /opt/jboss/wildfly

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
#ADD wildfly /opt/jboss/wildfly
ADD MyHotel-FrontEnd /opt/jboss/MyHotel-FrontEnd
ADD MyHotel-BackEnd /opt/jboss/MyHotel-BackEnd
ADD startup.sh /opt/jboss
#RUN ls /opt/jboss
#RUN yum -y install maven && yum -y clean all

ADD MyHotel-FrontEnd/dist /opt/jboss/wildfly/welcome-content
RUN ls /opt/jboss/wildfly/welcome-content

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
#ENV LAUNCH_JBOSS_IN_BACKGROUND true
# Expose the ports we're interested in
#EXPOSE 8080
#EXPOSE 9990

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
#RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
CMD ["/opt/jboss/startup.sh"]