# saving-client

Spring Boot restTemplate client to fetch all transactions from Starling Bank API, calculate the round-up and save the amount into a new savings goal.


## How is it working?

1) Spring boot will start a new local server and ask the Main method to automatically fetch all transactions.

2) An interceptor will capture the response from the API and call the RoundUp method.

3) RoundUpController will inject the service to create a new savings goal and finally save it using the API.



## How to build the project?

This compressed file already contains a packaged JAR file for you. It is inside the target folder > registration-service.jar
If you want to rebuild the project, you can do:

```
mvn clean install
```

## How to run the project?

Run this command to start the Spring Boot application:

```
    java -jar target/save-client.jar

```

You should see some output and the success message when the calculated amount is saved:

```
You successfully saved 55.04£ into your new saving goal
```

## API Token expire

The token used by this repository is not perpetual. If you get 403 error while testing, please contact me at:
nizar.bousebsi@gmail.com. I will refresh the Token for you.

## See it working


[![asciicast](https://asciinema.org/a/6FCEm5TsaOe3MZJdvYuskDaF7.png)](https://asciinema.org/a/6FCEm5TsaOe3MZJdvYuskDaF7)
