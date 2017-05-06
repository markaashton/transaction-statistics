# N26 BE Code Challenge

## Project
Author: Mark Ashton

Date: April 2017

A micro service that provides a ReST API for adding transactions and gathering statistics

## Requirements
Folder ./requirements contains requirements

## Overview
The micro service is implemented using Spring Boot and Java 8. Both endpoints execute in constant time and memory ( O(1) ).

To achieve this, a statistics gathering thread runs every n milliseconds (specified in application.properties) to calculate statistics.

To ensure thread-safety, a ConcurrentLinkedQueue contains transactions, immutable objects are used where possible, and synchronized blocks
protect critical sections.

The project is split into the following packages:

- priv.mashton.n26: contains the main Spring Boot application
- priv.mashton.n26.controllers: contains rest controllers for transactions and statistics.
- priv.mashton.n26.dtos: contains dtos (e.g. request/response dto's)
- priv.mashton.n26.model: contains models and their associated logic
- priv.mashton.n26.service: services for transactions and statistics
- priv.mashton.n26.statistics: contains implementation for a statistics thread that calculates and gathers statistics. The period of collection is 
specified in application.properties (in milliseconds).

Overview of folder structure:

- ./ : contains scripts for building and managing the service (start|stop|status) as well as the Maven pom file
- ./requirements: contains a copy of the requirements
- ./src/main: implementation
- ./src/test: automated tests (see testing section for an overview of tests)
- ./target: contains target jar (see the following sections for building/running)
- ./tests: contains test files (e.g. curl scripts, postman collection)

### Building

build.sh: Execute this bash script to build the project, run automated tests, and package the micro service into a jar in the target folder.

### Running

service.sh: This bash script supports start|stop|status operations. Executing service.sh without parameters will display information on using service.sh with parameters.

run.sh: This bash script also runs the microservice (ctrl-c to exit) but does not support stop or status.

Note: service.sh does not work on Microsoft Windows systems, therefore please use run.sh for Windows and Git Bash desktop application.

### Configuration

The period by which the Statistics gathering thread updates statistics can be configured. Amend parameter "app.updateperiodms" and change to desired value (in milliseconds).
The configuration file (application.properties) is located under the root folder (where run.sh/service.sh are located)

### Testing

- A suite of automated tests are provided (unit tests, integration tests, and application tests).
- No performance or scaling tests are included.
- Some rudimentary ruby scripts are included in the test folder to add a transaction and get statistics

## Notes

- A zero or negative amount for a transaction is not allowed. This will result in an IllegalArgumentException or BadRequest depending on the acting client.
It would be possible to easily change this if transactions are to be negative (e.g. for refunds).
- The return types for /statistics are specified as doubles (except for count). In the example response, they have no decimal places. I took the decision to 
allow a full double in the response with the exception of average which has two decimal places. This can be changed easily.
- I added an interface (Statistics.java) for the StatisticsGather.java class as this made sense for the statistics package in terms of
making the code easier to read and understanding the contract. However, I did not add interfaces for StatisticsService, 
TransactionsService (etc) as I didn't feel it added value.

## Todo

- add more application, integration and unit testing to cover more cases, including corner cases
- refactor and improve ruby scripts (or utilise an off-the-shelf framework) to automate black box testing

## Update on Saturday 6th May (prior to receiving any feedback)

- I added a JMeter test plan to the project under ./tests which starts 50 threads that hit both endpoints over a period of 2 minutes. For the timestamp, the test plan was manually edited to use the current
unix time so that statistics are non-zero. Future updates to the JMeter test plan would dynamically set unix timestamp. A screenshot of the results are in the tests folder (png file).
- The current implementation is inefficient. I only had around 4 hours to write the code so I had to roll with an inefficient solution so I could spend time on automated tests to demonstrate testing at the expense of
an inefficient algorithm and code that can be refactored to reduce the class count and make the coder easier to read.
- Some improvements I could make:
    - The statistics gathering algorithm traverses the entire collection (the queue) every time it runs. This is inefficient and becomes more so as the collection increases in size. It would be better to have another thread
     that processes queued incoming transactions and stores them in a timestamp sorted collection. This means that the statistics gatherer only has to traverse the first sixty seconds of the collection. This obviously has to 
     be made threadsafe. The sort/add algorithm must also receive special attention when adding queued transactions.
    - StatisticsGatherer and StatisticsRunner can be merged into one class (two are unnecessary). It would also be wise to name the thread which I don't currently do.    
    - Consideration should be made to merge TransactionStatistics in model and StatisticsResponse in dto. I like to seperate dto from model but there's a lot of redundancy here (DRY: Don't Repeat Yourself)
    - Consider using Spring TaskExecutor instead of a manual coded Thread/Runnable.
    - Sort out synchronization blocks/keywords (currently, it's overkill)
    - Seperate code to get statistics from the object that processes statistics. Because of JVM and Java's monitor, the current implementation is inefficient here because the request to get statistics will be delayed if statistics are being gathered.
