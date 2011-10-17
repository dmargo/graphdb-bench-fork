export MAVEN_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xms256M -Xmx256M"
mvn -e exec:java -Dexec.mainClass="com.tinkerpop.bench.BenchmarkSuite" -Dexec.args=""
