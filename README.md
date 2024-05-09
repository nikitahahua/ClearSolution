## Cear Solution Java practical test assignment

### Requirements:

1. It has the following fields:
1.1. Email (required). Add validation against email pattern
1.2. First name (required)
1.3. Last name (required)
1.4. Birth date (required). Value must be earlier than current date
1.5. Address (optional)
1.6. Phone number (optional)
2. It has the following functionality:
2.1. Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
2.2. Update one/some user fields
2.3. Update all user fields
2.4. Delete user
2.5. Search for users by birth date range. Add the validation which checks that “From” is less than “To”.  Should return a list of objects
3. Code is covered by unit tests using Spring 
4. Code has error handling for REST
5. API responses are in JSON format
6. Use of database is not necessary. The data persistence layer is not required.
7. Any version of Spring Boot. Java version of your choice
8. You can use Spring Initializer utility to create the project: Spring Initializr

Installation guide

1. clone repository

```
git clone https://github.com/nikitahahua/ClearSolution.git
```

2. Set up a PostgreSQL server.
Change the configuration of application.properties (default username and password: postgres)

3. Launch

```

mvn package

cd target

java -jar ClearSolution-0.0.1-SNAPSHOT.jar

```

### Results: 

#### Crete valid user

<img width="1000" alt="image" src="https://github.com/nikitahahua/ClearSolution/assets/110698480/0d1224b8-a09c-4521-b515-689c845db3b7">

#### Creating a user that is not old enough

<img width="877" alt="image" src="https://github.com/nikitahahua/ClearSolution/assets/110698480/d3a04b39-11d9-4607-99af-7c336228933c">

#### Delete valid user

<img width="1020" alt="image" src="https://github.com/nikitahahua/ClearSolution/assets/110698480/70f9baa0-460f-48d9-be4e-faacfadf65a8">

#### Getting users sorted by birth date

<img width="878" alt="image" src="https://github.com/nikitahahua/ClearSolution/assets/110698480/d2545fc4-3fd4-45a4-8e0b-48971cfb91ec">

#### Lets update validuser@example.com

address and phoneNumber added and updated Birth Date and Name

<img width="848" alt="image" src="https://github.com/nikitahahua/ClearSolution/assets/110698480/82494fc2-a1c2-4168-b631-7f895cb1c330">



