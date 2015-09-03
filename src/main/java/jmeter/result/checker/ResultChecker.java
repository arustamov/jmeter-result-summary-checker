package jmeter.result.checker;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jmeter.result.checker.assertion.Assertion;
import jmeter.result.checker.matcher.MatcherEnum;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.runner.JUnitCore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Anton Rustamov (arustamov) on 7/30/2015.
 */
public class ResultChecker {

    private static String resultFile;
    private static String assertionFile;
    private Collection<Object[]> csvRecordAssertionPairs = new ArrayList<>();

    public ResultChecker() {
        if (resultFile == null && assertionFile == null) {
            resultFile = System.getProperty("resultFile");
            assertionFile = System.getProperty("assertionFile");
            checkArgs();
        }
    }

    public static void main(String[] args) throws IOException {
        processArgs(Arrays.asList(args).iterator());
        checkArgs();
        JUnitCore.main("jmeter.result.checker.test.CSVRecordAssertionTest");
    }

    public Collection<Object[]> getCSVRecordAssertionPairs() {
        List<CSVRecord> csvRecords = readCSVRecords();
        List<Assertion> assertions = readAssertions();
        populateCSVRecordAssertionPairs(csvRecords, assertions);
        return csvRecordAssertionPairs;
    }

    public void populateCSVRecordAssertionPairs(List<CSVRecord> csvRecords, List<Assertion> assertions) {
        for (CSVRecord csvRecord : csvRecords) {
           for (Assertion assertion : assertions) {
               if (assertion.appliesTo(csvRecord)) {
                   Object[] csvRecordAssertionPair = {csvRecord, assertion};
                   csvRecordAssertionPairs.add(csvRecordAssertionPair);
               }
           }
        }
    }

    private List<CSVRecord> readCSVRecords() {
        try {
            BufferedReader resultReader = new BufferedReader(new FileReader(resultFile));
            String[] headers = resultReader.readLine().split(",");
            List<CSVRecord> records = CSVFormat.DEFAULT.withHeader(headers).parse(resultReader).getRecords();
            return records;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Assertion> readAssertions() {
        if (assertionFile.endsWith("json")) {
            return readAssertionsJson();
        }
        else {
            return readAssertionsString();
        }
    }

    private List<Assertion> readAssertionsJson() {
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(MatcherEnum.class, new JsonDeserializer<MatcherEnum>() {
                @Override
                public MatcherEnum deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
                    return MatcherEnum.fromMatcherMethodName(json.getAsString());
                }
            }).create();
            BufferedReader assertionReader = new BufferedReader(new FileReader(assertionFile));
            Type listType = new TypeToken<List<Assertion>>(){}.getType();
            List<Assertion> assertions = gson.fromJson(assertionReader, listType);
            return assertions;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Assertion> readAssertionsString() {
        try {
            List<Assertion> assertions = new ArrayList<>();
            BufferedReader assertionReader = new BufferedReader(new FileReader(assertionFile));
            String line;
            while ((line = assertionReader.readLine()) != null) {
                Assertion assertion = Assertion.fromString(line.trim());
                assertions.add(assertion);
            }
            return assertions;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void processArgs(Iterator args) {
        while (args.hasNext()) {
            String nextArg = (String) args.next();
            if (nextArg.equalsIgnoreCase("--result-file")) {
                if (!args.hasNext()) {
                    throw new IllegalArgumentException("Missing result file");
                }
                resultFile = (String) args.next();
            }
            else if (nextArg.equalsIgnoreCase("--assertion-file")) {
                if (!args.hasNext()) {
                    throw new IllegalArgumentException("Missing assertion file");
                }
                assertionFile = (String) args.next();
            }
            else {
                throw new UnsupportedOperationException("Unsupported option: " + nextArg);
            }
        }
    }

    private static void checkArgs() {
        if (resultFile == null) {
            throw new IllegalArgumentException("Missing result file");
        }
        if (!(new File(resultFile).exists())) {
            throw new IllegalArgumentException("Cannot find specified result file: " + resultFile);
        }
        if (assertionFile == null) {
            throw new IllegalArgumentException("Missing assertion file");
        }
        if (!(new File(assertionFile).exists())) {
            throw new IllegalArgumentException("Cannot find specified assertion file: " + assertionFile);
        }
    }
}
