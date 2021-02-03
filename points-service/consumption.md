# User Points Service

## Overview
The service at root /user-points is an API for handling transactoins related to user reward points.
Integration point for this API is MongoDB.
This API responds in JSON only.
If the call to this API is successful, 200 as the response code.

## Prerequisites
* Mongo is installed and an instance is runing in the localhost of the machine that you intend to run this service on
* For your reference a mongo client installable can be [found here](https://www.mongodb.com/try/download/community?tck=docs_server)
* Install this Mongo client and then from the top left menu, navigate to:
>>	Connect -> Connect to -> New Connection -> Fill in connection fields individually -> connect (leave the default settings in).
							
- Run the application using your favourite IDE, or run the following commands in a terminal at the root of the project:
	>> * mvn clean install
	>> * mvn spring-boot:run

This starts the points service on your local host on port 8080.

## Accessing the API:

There are a total of 4 endpoints available on the API:

>> * The API has an exposed GET endpoint available at "/points" that provides a paginated response of all the points information stored in the database.

>> * Another GET endpoint is avaliable at "/health" which gives the consumer the current status of the API in a JSON format.

>> * A POST endpoint is available at "/points/earn" which records an "earning" scenario and creates appropriate database entries.

>> * A POST endpoint is available at "/points/redeem" which records an "redeeming" scenario and creates appropriate database entries.

Once the API is running, the endpoints can be invoked either by hitting the endpoint from a client such as Postman, or, tried out via the Swagger UI that has been built into the API.

The swagger endpoint can be [accessed through this link](http://localhost:8080/points-service/swagger-ui/index.html?configUrl=/points-service/v3/api-docs/swagger-config) (while the API is running on localhost).

If using a client such as Postman, the API can be accessed at [this link as a GET resource](http://localhost:8080/metricshttp://localhost:8080/points-service).

- Refer [Notes Section](#notes) at the end of this page before querying any data.

## Parameters

A detailed documentation for parameters is provided on the swagger page.

#### Resources

#### earn:
```http
POST /points/earn
```
#### Request Body

| Field Name | Data Type | Example Input Values | Optional |
| ------------------ |:------------------:|:--------------------------:|:--------------:|
| `accountId` [[1]](#notes)| `long` | 1111 | `N` |
| `createDate` | `Date` | 2021-02-03T05:33:04.107+00:00 | `Y` |
| `userName` [[1]](#notes)| `string` | bobTheBuilder | `N` |
| `points` [[1]](#notes)| `int` | 100 | `N` |

#### redeem:
```http
POST /points/redeem
```
#### Request Body

| Field Name | Data Type | Example Input Values | Optional |
| ---------- |:---------------:|:---------------------:|:----------------------------:|
| `accountId` [[1]](#notes)| `long` | 1111111 | `N` |
| `points` [[1]](#notes)| `int` | 100000 | `N` |

### Notes
1. Use _**all**_ of the required parameters in the request object.
2. For the query endpoint **Page** is an indexed field and value starts from **"0"**.
3.  Default value for **Size** is **"50"** and **MAX** value is **"50"**.
