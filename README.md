Const Cont Web
==============
This website follows the Spring MVC pattern and is built using Spring Boot. 
It makes use of the JPA implementation Hibernate for Object Relational Mapping and CRUD.
MySQL is used to store entities like Contact Information, Testimonials, etc. 
Thymeleaf is used for templating and view resolution, and Bootstrap for styling.


### Requirements 
* Apache Tomcat
* Spring Boot 5.x
* Gradle
* MySQL 7


### Building
The project is built using Gradle. To build, run:
``` commandline
$ gradle build
```
The application expects MySQL daemon to be running the background. 
On Ubuntu, this can be started via *systemd*.

On Windows, MySQL can be managed as a service.

See [application.properties](https://github.com/uardoq/llc-web/blob/master/src/main/resources/application.properties) 
for connectivity information.

### Running
``` commandline
$ gradle run
```

### TODO
* implement Spring Security
* improve on CSS styles
