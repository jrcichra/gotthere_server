FROM openjdk:11                                 
WORKDIR /app                                    
COPY target/gotthere_server-0.0.1-SNAPSHOT.jar .       
CMD java -jar -Dspring.datasource.url=jdbc:mariadb://mariadb.mariadb:3306/gotthere_database gotthere_server-0.0.1-SNAPSHOT.jar