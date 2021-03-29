# Install postgresql
- brew install postgresql
- brew services start postgresql
- psql postgres

# Setup Database
- CREATE DATABASE assistant;
- \q
- run the sql file for initial setup
  - psql -h 127.0.0.1 -d assistant -f setup.sql
    
# Build and Run Application
- mvn clean install
- mvn spring-boot:run

# API Postman Collection
- https://www.getpostman.com/collections/af0eba51a6cde882d523