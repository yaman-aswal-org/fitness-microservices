# FITNESS MICROSERVICES

**Install in WINDOWS/MACOS:**
* MongoDB Compass Desktop 
* Jdk-17
* IntelliJ IDEA 

**Services Order (start up)**:
1. Config Server [configserver] - where all services configurations.
2. Eureka Service Registry [eureka] - discovery all services.
3. Api Gateway - [gateway] - where are routes defines for business services.
4. Business Services: features services used for business logic.
   * user-service
   * activity-service
   * ai-service

### Routes:
1. Config Server :-
   * Local → http://localhost:8761/ 
2. PgAdmin :-
   * Local → http://localhost:5050/
3. Api Gateway :-
   * Local → http://localhost:8080/

### Databases
1. **Postgres DB (Local):**
   * Host: postgres
   * Port: 5432
   * Username: admin
   * Password: admin123

2. **Mongo DB (Local):** 
   * Username: admin
   * Password: admin123

### Queue: 
* RabbitMQ Admin: 
   * Local → http://localhost:15672/
   * Exchange: fitness.exchange
   * Queue Name: activity.queue


   


