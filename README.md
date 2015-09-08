# jmeter-result-summary-checker
Tool to check JMeter test results summary according to assertions provided

Usage:

There are 2 possible ways to run JMeter test results summary checker:

1. Run executable jar from command line just to apply assertions provided
2. Run maven test to apply assertions provided and get surefire report (both xml and html)

How to run:

1. Command Line:

mvn package -DskipTests to get executable jar (assembly)
go to target and run java -jar jmeter-result-summary-checker-1.1.0.jar --result-file $PATH_TO_JMETER_RESULTS_SUMMARY_CSV_FILE --assertion-file $PATH_TO_JMETER_RESULTS_SUMMARY_ASSERTION_JSON_FILE

2. Maven:

mvn site -DresultFile=$PATH_TO_JMETER_RESULTS_SUMMARY_CSV_FILE -DassertionFile=$PATH_TO_JMETER_RESULTS_SUMMARY_ASSERTION_JSON_FILE

How to generate JMeter results summary:

1. Download and extract JMeter plugins standard set from http://jmeter-plugins.org/
2. Unpack according to instructions
3. Go to $JMETER_HOME/lib/ext
4. From command line run java -jar CMDRunner.jar --tool Reporter --generate-csv $PATH_TO_JMETER_RESULTS_SUMMARY_CSV_FILE --input-jtl $PATH_TO_JMETER_RESULTS_CSV_FILE --plugin-type SynthesisReport

Example of assertion json file can be found in test resourced folder