#!/bin/bash
set -e


#
# AGENT configuration
#

AGENT=/usr/local/java/profiler4j/agent.jar
AGENT_OPTS=


#
# Check AGENT
#

if [ ! -f $AGENT ]; then
	echo "Profiler4j not found: Please download it from http://profiler4j.sourceforge.net/"
	echo "and copy it to /usr/local/java/profiler4j"
	exit 1
fi


#
# Run the profiler
#

export MAVEN_OPTS="-javaagent:$AGENT $AGENT_OPTS -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xms512M -Xmx512M"
mvn -e exec:java -Dexec.mainClass="com.tinkerpop.bench.BenchmarkSuite" -Dexec.args="$*"

