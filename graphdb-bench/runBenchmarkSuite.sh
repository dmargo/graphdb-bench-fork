export MAVEN_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xms512M -Xmx512M"
mvn -e exec:java -Dexec.mainClass="com.tinkerpop.bench.BenchmarkSuite" -Dexec.args="--neo --dijkstra"
