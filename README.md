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
    * Local → http://localhost:8888/
2. Eureka :-
   * Local → http://localhost:8761/
3. PgAdmin :-
   * Local → http://localhost:5050/
4. Api Gateway :-
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


### Render (Prod) 
1. Config Server 
   * PROD → https://configserver-k9gt.onrender.com/
   * SPRING_PROFILES_ACTIVE=prod,native
   * PORT = 8888
2. Eureka
   * PROD → https://eureka-q8n6.onrender.com/
   * SPRING_PROFILES_ACTIVE=prod
   * PORT = 8761
3. User Service
    * PROD → https://userservice-gjy6.onrender.com
    * SPRING_PROFILES_ACTIVE=prod
    * PORT = 8082
4. Activity Service
   * PROD → https://activity-service-2g1d.onrender.com
   * SPRING_PROFILES_ACTIVE=prod
   * PORT = 8082
5. Ai Service
   * PROD → https://ai-service-l866.onrender.com
   * SPRING_PROFILES_ACTIVE=prod
   * PORT = 8083


