# Project Title

Provide real time price statistics for the last 60 seconds to monitor the incoming prices.

## Getting Started

The project is based on a small web service doing the following operations.

* Receive a tick for an instrument.
* Statistics based on the ticks of all instuments for last 60 seconds.
* Statistics based on the ticks of one instument for last 60 seconds.

### Prerequisites

What things you need to install the software and how to install them

```
JAVA 1.8
Spring Boot
Maven
```

### Installing

Execute the following file to start the application.

```
SolactiveCodingTestApplication.java
```

Examples

```
POST /ticks

{
"instrument": "IBM.N",
"price": 143.82,
"timestamp": 1478192204000
} 
```



```
GET /statistics

{
"avg": 143.2,
"max": 143.2,
"min": 143.2,
"count": 1
}
```

```
GET /statistics/{instrument_identifier}
{
"avg": 100,
"max": 200,
"min": 50,
"count": 10
}
```


## Running the tests

Tests for the application are located in the below file. Execute the file to check the behaviour of tests.

```
SolactiveCodingTestApplicationTests.java
```

## Assumptions

* Payload of recieving a tick is always valid and has necessary data.
* Instrument's identifier is case sensitive for calculating the statistics.
* Data store(Java Heap) has enough storage to store the statistics for each second.
* Every instrument's tick timestamp is adjusted to the nearest second and statistics are calculated based on that.


## Improvements

* Defining a clean up process which clears the older data from data store.
* Using more effective ways for calculation of statistics either by using Map Reduce or Apache Spark or Kafka Streams.
* Using a TimeSeries Data store, which helps in better performance.


## Feedback

I loved the task. It tests various part of coding skills like multithreading, design practices, SOLID priciples and problem solving.


## Note

* GET API for both statistics, always fetches data for past 60 seconds and consolidate it. So, it is treated as O(1) complexity, as it takes constant time execution for any input.