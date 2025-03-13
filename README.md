# Bank Transaction System Project (Assignment)

## I. Project Overview
This project is a bank transaction system developed based on Spring Boot, which is used to handle business logic related to bank transactions, such as saving, querying, deleting, and modifying transaction records. All data is recorded in the cache.

## II. Introduction to the Technology Stack
### 1. Basic Frameworks
- **Spring Boot**: Version 3.4.1.
- **JUnit**: Used for writing and running unit tests.
- **Mockito**: Version 4.7.0, used to create and manage mock objects in unit tests for convenient isolation testing.

### 2. Integration Tools
- **Lombok**: Simplifies Java code through annotations, reducing the writing of boilerplate code such as getter and setter methods.
- **MapStruct**: Version 1.6.3, a code generator used to simplify the mapping conversion between Java Beans, improving code readability and maintainability.
- **Hutool**: Version 5.8.35, a Java utility library that provides a rich set of utility classes and methods, such as object utilities and string utilities, facilitating various operations during development.
- **SpringDoc**: Version 2.7.0, used to automatically generate and display documentation for RESTful APIs, making it convenient for developers and testers to view and test interfaces.

## III. Introduction to the Directory Structure
### Introduction to the General Business Directory
**`src/main/java/`**:
- **`com.bank.common.exception`**: General global exception handling, which can be used not only for the current transaction business but also for other future businesses.
- **`com.bank.common.vo`**: General return result class.

### Introduction to the Transaction Business Directory
**`src/main/java/`**:
- **`com.bank.trade.infrastructure`**: Basic settings for the transaction business. Here, "session" indicates that data is processed in the cache, and "mysql" indicates that data is processed in the database (not implemented in this case).
- **`com.bank.trade.domain`**: The domain layer of the transaction business. The domain layer is used to implement a complete command operation. In actual development, transactions and exceptions need to be handled at this layer (since no database is used in this case, transactions are not handled).
- **`com.bank.trade.application`**: The application layer of the transaction business. This layer is divided into two types of applications: command and query. In actual development, multi - threading and locks are all handled at this layer. This layer does not handle transactions, and multiple transactions will not be passed. Two commands with strong consistency should not be placed in one application.
- **`com.bank.trade.controller`**: The presentation layer of the transaction business. This layer is an entry layer, which serves as the entrance for external applications.

### Other Notes
- For other subsequent businesses, just like the transaction business, a complete four - layer structure should be established.
- Each business should provide a custom exception enumeration class for the current business (similar to [TradeResponseCode.java](src%2Fmain%2Fjava%2Fcom%2Fbank%2Ftrade%2Fdomain%2Fexception%2FTradeResponseCode.java) in the transaction business), which should be as detailed as possible. This not only makes the interface robust enough but also provides more friendly and precise interaction prompts for users.

## IV. Run the Project on PC
### 1. Environment Requirements
- Java 21
- Maven

### 2. Steps
1. Clone the project to the local machine:
```bash
git clone https://github.com/wilbur-ltf/bank.git
```
2. Open the project in IntelliJ IDEA, and click the button in the upper - right corner as shown in the figure to start the project:
![img](https://github.com/user-attachments/assets/f5460fe8-b87c-4e5b-8f1d-34a5f58f1cd1)

## V. Swagger Testing
### 1. Access Address
http://localhost:8080/swagger-ui/index.html
The effect is shown in the figure:
![img_1](https://github.com/user-attachments/assets/efae0244-f134-47a5-b70b-29daa3c95cba)

### 2. New Addition Test
![img_5](https://github.com/user-attachments/assets/f0b8875c-e686-4fb0-9a16-4ece1e3f2f56)
![img_2](https://github.com/user-attachments/assets/751cd6d2-7a6f-4925-a03c-fadb89ca9b03)

### 3. Modification Test
![img_3](https://github.com/user-attachments/assets/7f5e4178-80b3-4782-a6a1-3b34158cdc76)
![img_4](https://github.com/user-attachments/assets/46369d3d-fe46-451a-8b1d-bbb8c9624048)

### 4. Query Test
![img_6](https://github.com/user-attachments/assets/db062c19-69bb-48c2-8e90-af7e1152f3c3)

### 5. Deletion Test
![img_7](https://github.com/user-attachments/assets/73aedfa5-4977-48a3-aea0-00b5077a18f9)

## V. Unit Testing
### 1. Location of the Test Class
![img_8](https://github.com/user-attachments/assets/50bda491-c15b-4cec-841b-dcf2fc66ddd4)

### 2. Testing Effect
![img_9](https://github.com/user-attachments/assets/9d08a0f6-0a49-4928-8526-67c225d524a4)

### 3. Instructions for What to Do (TODO) in Unit Testing
In actual development, it is necessary not only to provide unit tests for the controller but also for the application and domain layers. These unit tests are essential. Moreover, every exception branch should be covered. Since this part of the code is similar to the unit tests mentioned above, due to time constraints (there has been particularly heavy overtime work in the company recently), I haven't written them yet. I'm making this special note to explain the situation.  
## VI. Stress Testing
### 1. Location of the Test Class
![img_10](https://github.com/user-attachments/assets/8e540b0f-a5f7-4378-95e7-f3da20a17218)

### 2. Testing Results of the Stress Test
![img_11](https://github.com/user-attachments/assets/43469377-f331-4e14-9e18-5c6b00fd2cd7)
![img_12](https://github.com/user-attachments/assets/c8a4f515-a236-4234-a827-399ec79914d1)
![img_13](https://github.com/user-attachments/assets/369fa52a-5a11-4805-a13e-8b5f2306de1f)
![img_14](https://github.com/user-attachments/assets/045559c6-e88c-4805-bbcc-a431113cdd08)

## VII. Project Deployment
### 1. Write the Dockfile. For details, please refer to [Dockerfile](Dockerfile).
The location is shown in the figure:
![img_15](https://github.com/user-attachments/assets/beacfa33-0ee1-4737-a99d-0c7848697c48)

### 2. Install Docker
The steps are omitted here.
### 3. Build the container
docker build -t bank:1.0 .
![img_16](https://github.com/user-attachments/assets/066f803a-a6a4-43ba-9a46-186387a86760)

### 4. Start the container
docker run bank:1.0

## VIII. Other Instructions
### 1. Instructions on the Project's Extensibility
The four-layer structure of DDD (Domain-Driven Design) can maximally separate the technology stack (infrastructure layer) from the business (domain layer). According to the principle of open-closed, the domain layer is only responsible for calling interfaces and does not care about the used technology stack and data sources. This greatly enhances the extensibility of the code.
### 2. Instructions on the System's Concurrency
In the stress test, I used multithreading. In actual development, to improve the system's concurrency, in addition to using multithreading and improving the utilization rate of server resources, it is usually necessary to use methods such as caching and message queues. This part is not reflected in the current case, and I am also making this explanation. 
