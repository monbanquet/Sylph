#!/bin/bash

mvn clean verify -Psign
mvn release:prepare -DskipTests -Prelease
mvn release:perform -DskipTests -Prelease