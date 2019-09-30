#!/bin/bash

mvn release:clean
mvn release:prepare -Prelease
mvn release:perform -Prelease