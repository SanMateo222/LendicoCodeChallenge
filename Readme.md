# Lendico Loan Repayment App - code challenge readme.

## This app is done in Maven with Java 8 and Spring Boot.

## Running the app:
### 1) Clone following repository:
```sh
git clone https://github.com/SanMateo222/LendicoCodeChallenge
```

### 2) Build & run the app
```sh
cd <git_directory>/LendicoCodeChallenge
```
```sh

mvn install && java -jar target/LendicoLoanRepaymentApp-1.0-SNAPSHOT.jar
```

### 3) Request
Using Http client (ex. Postman), run POST request for following url:
```sh
http://localhost:8080/generate-repayment-plan
```
with following body in json format:
```sh

{
  "loanAmount": "5000",
  "nominalRate": "5.0",
  "duration": 24,
  "startDate": "2018-01-01T00:00:00Z"
}
```

Due to incoming deadline for this test, there are still some things to be done.
More scenarios testing and exception handling should be improved.
Project could be also posted online, on heroku for example, which I didn't do due to lack of time, but can do if requested.
