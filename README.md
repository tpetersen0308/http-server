# http-server
[![Build Status](https://travis-ci.com/tpetersen0308/http-server.svg?branch=master)](https://travis-ci.com/tpetersen0308/http-server)
A basic HTTP server written in java.

## Requirements

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

## Installation

Clone or download the repository.

## Basic Usage

Run tests:

`mvn test`

Create the project:

`mvn package`

Start the Server:

`./bin/http_server <PORT>` (Defaults to running on port 5000)

or

`java -cp target/http-server-1.0-SNAPSHOT.jar server.Main <PORT>`