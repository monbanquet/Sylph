#!/bin/bash

mvn clean verify
mvn release:prepare -P release -DskipTests
mvn release:perform -P release -DskipTests