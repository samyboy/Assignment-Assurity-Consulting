package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        String propertFilePath = System.getProperty("user.dir") + "\\src\\test\\java\\resources\\global.properties";
        FileInputStream fis = new FileInputStream(propertFilePath);

        prop.load(fis);
        System.out.println("Value for KEY" + key + " is ====>" + prop.getProperty(key));
        return prop.getProperty(key);
    }


    /*
     * public RequestSpecification requestSpecification(String requestType) throws
     * IOException { if (requestType.equals("Add")) { baseURLPropertyFile =
     * "baseUrlAdd"; } else if (requestType.equals("Get")) { baseURLPropertyFile =
     * "baseUrlGet"; } else if (requestType.equalsIgnoreCase("Update")) {
     * baseURLPropertyFile = "baseUrlUpdate"; } else if
     * (requestType.equalsIgnoreCase("Delete")) { baseURLPropertyFile =
     * "baseUrlDelete"; } else {
     * System.out.println("Please provide request type in your feature file given");
     * }
     *
     * System.out.println("Value"+baseURLPropertyFile);
     *
     * //DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm-ss").format(LocalDate.now());
     *
     * String dateFormat =
     * DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now()); String
     * fileName=getGlobalValue("logFile")+"eventLog-" + dateFormat + ".log";
     * PrintStream log = new PrintStream(new FileOutputStream(fileName, true));
     * System.out.println("***Invoked URL***"+getGlobalValue(baseURLPropertyFile));
     * req = new
     * RequestSpecBuilder().setBaseUri(getGlobalValue(baseURLPropertyFile))
     * .addFilter(RequestLoggingFilter.logRequestTo(log))
     * .addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(
     * ContentType.JSON).build(); return req;
     *
     *
     * }
     */

    public RequestSpecification requestSpecification() throws IOException {

        if (req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
//		.addQueryParam("key", "qaclick123")

    }

    public String getJsonPath(Response response, String key) {
        String value = null;

        String resp = response.asString();

        try {
            JsonPath js = new JsonPath(resp);

            value = (js.get(key)).toString();

        }

        catch (Exception e) {
            System.out.println("Exception : " + e);
        }

        return value;
    }

    public String getPropertyFromArrayInJsonWhereKeyIs(Response response, String arrayKey, String key, String whereKey, String whereValue) {
        String value = null;

        String pathToQuery = arrayKey + ".find {it." + whereKey + " == '" + whereValue + "'}";
        if (key != null) {
            pathToQuery += "." + key;
        }
        return this.getJsonPath(response, pathToQuery);
    }


}
