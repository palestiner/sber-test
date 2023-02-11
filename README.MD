## Usage

Java 17 **required**

To start a postgresql database in a docker container, run:

```
docker-compose -f ./dev-compose.yaml up -d
```

To run app in docker:

```
mvn clean package
docker-compose up -d
```
