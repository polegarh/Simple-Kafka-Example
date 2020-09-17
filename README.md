## Description 
This simple example will help you understand Kafka, but first what even is Kafka?  

## What is Kafka?
Kafka is an event streaming platform. Moreover, Kafka combines three key capabilities so you can implement your use cases for event streaming end-to-end with a single battle-tested solution:

* To publish (write) and subscribe to (read) streams of events, including continuous import/export of your data from other systems.
* To store streams of events durably and reliably for as long as you want.
* To process streams of events as they occur or retrospectively.

And all this functionality is provided in a distributed, highly scalable, elastic, fault-tolerant, and secure manner. Kafka can be deployed on bare-metal hardware, virtual machines, and containers, and on-premises as well as in the cloud. You can choose between self-managing your Kafka environments and using fully managed services offered by a variety of vendors.

## How does Kafka work in a nutshell?
Kafka is a distributed system consisting of servers and clients that communicate via a high-performance TCP network protocol. It can be deployed on bare-metal hardware, virtual machines, and containers in on-premise as well as cloud environments.

## Main Components

#### Producer publishes (writes) a stream of events to one or more Kafka topics

#### Consumer subscribes to (reads) one or more topics and processes the stream of events produced to them

## The Goal of the Excercise
The main goal of this excersie is to read data from the text file :arrow_right: compile it in json format :arrow_right: feed kafka producer :arrow_right: retrieve data from kafka consumer 

#### Additional Notes
For definitions I have referenced the official <a href="https://kafka.apache.org/intro"> Apache Kafka Documentation </a>

Executable Files: 
* Customer.java
* Product.java
* Refund.java 
* Sales.java

