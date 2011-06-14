export MAVEN_OPTS="-server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xms6G -Xmx6G"
mvn -e exec:java -Dexec.mainClass="com.tinkerpop.bench.BenchmarkSuite" -Dexec.args=""
