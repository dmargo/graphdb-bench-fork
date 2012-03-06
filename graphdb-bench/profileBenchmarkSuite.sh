#!/bin/bash
set -e


#
# JRat configuration
#

JRAT=/usr/local/java/shiftone-jrat.jar
JRAT_OPTS="-Djrat.factory=org.shiftone.jrat.provider.tree.TreeMethodHandlerFactory"


#
# Check JRat
#

if [ ! -f $JRAT ]; then
	echo "JRat not found: Please download it from http://jrat.sourceforge.net/"
	echo "and copy the .jar file to $JRAT"
	exit 1
fi


#
# Run the profiler
#

export MAVEN_OPTS="-javaagent:$JRAT $JRAT_OPTS -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xms512M -Xmx512M"
mvn -e exec:java -Dexec.mainClass="com.tinkerpop.bench.BenchmarkSuite" -Dexec.args="$*"

