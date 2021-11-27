#Geolocalización de Colonias Felinas - back

Service that registers cat colonies in Madrid and their feeding to keep track of them.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Software you will need to install.

```
1. Install Java JDK11

2. Clone repository: https://github.com/LescanoMicaela/geolocalizacion-back.git 

3. Apache Maven package and variable environment 

	- MAVEN_HOME=C:\{your_system_route}\apache-maven-x.x.x
	- %MAVEN_HOME%\bin

4. Check your Maven config: mvn --version


```

## Starting application in local envioroment

1. Configure MySQL database and add properties to application.properties

3. Start the application: mvn spring-boot:run

2. Check the Swagger on your browser:

http://localhost:xxxx/swagger-ui/index.html


## Running tests

You can execute `mvn clean test`
