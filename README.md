# restassured-up42-test
Tested on: JDK 13.

##How to run
To run all tests from commandline:
```
mvn clean test
```

(!) Requirement to run from Intellij IDEA: lombok plugin should be installed
https://projectlombok.org/setup/intellij

##How to get fancy report
Run following command (after test run)
```
mvn allure:serve
```

##Problems found
* When saving workflow with ~ 500 characters long name 500 error is thrown (request should be validated before trying to save to data base)
* Once got gateway timeout when waiting for job to finish (better to check application or proxy logs) 
* Response after getting oauth toke is inconsistent (both lower camel case and snake case used for variables)
* Total processing time for workflow creation is always zero
* Date time in responses is UTC (I assume it's supposed to be so, but better to doublecheck)

##Testing code improvements
* Store project id and key far away from code (or pass as env variables) and encrypt
* TaskTest - add assertions and more test cases
* Add JSON schema validation
* Add logging (not just request / response)
* Config object can be stored in some base test instead of static access
* Static methods for clients might not be the best idea
* Reuse POJOs from devs code (debatable)