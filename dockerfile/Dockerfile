#pull java runtime
FROM 192.168.61.93/amar/jdk1.8.0_144-centos:latest

# Copy the files
ADD ./AmarPackTest.jar ./crapp/AmarPackTest/
ADD ./config/ ./crapp/AmarPackTest/config/
ADD ./logs/ ./crapp/AmarPackTest/logs/
ADD ./start.sh ./crapp/AmarPackTest/

WORKDIR /opt/crapp/AmarPackTest/
CMD ["sh", "./start.sh"]