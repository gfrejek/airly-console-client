import java.util.Arrays;
import java.util.regex.Pattern;

public class OptionParser {
    private AirlyMode mode = null;
    private String[] arguments;
    private boolean hasAPIkey = false;
    private String longitude = "";
    private String latitude = "";
    private String sensorid = "";
    private String apikey = "";
    private boolean history = false;


    public OptionParser(String[] arg) {
        this.arguments = arg;
    }

    public void checkArguments() {

        Pattern latitudePattern = Pattern.compile("--latitude=?-?\\d*\\.?\\d*");
        Pattern longitudePattern = Pattern.compile("--longitude=?-?\\d*\\.?\\d*");
        Pattern sensoridPattern = Pattern.compile("--sensor-id=?\\d*");
        Pattern apiKeyPattern = Pattern.compile("--api-key=?[a-z0-9]*");
        Pattern historyPattern = Pattern.compile("--history");

        int currentArgument = 0;        // I used "currentArgument" to support both "--latitude 50 --longitude 20"
                                        // and "--latitude=50 --longitude=20" argument types

        if (arguments.length == 1) {        // Case "--sensorid=5"
            if (sensoridPattern.matcher(arguments[currentArgument]).matches()) {
                mode = AirlyMode.Sensor;
                sensorid = getValue(arguments[currentArgument], ArgumentType.SensorID);
                if (sensorid.equals("")) throw new IllegalArgumentException(printHelp());
            }
        } else if (arguments.length >= 2) {             // Case "--latitude=5 --longitude=9"
            if (latitudePattern.matcher(arguments[currentArgument]).matches()) {
                latitude = getValue(arguments[currentArgument], ArgumentType.Latitude);
                mode = AirlyMode.Coordinates;

                if (latitude.equals("")) {
                    currentArgument++;
                    latitude = arguments[currentArgument];
                }

                currentArgument++;
                if (longitudePattern.matcher(arguments[currentArgument]).matches()) {

                    longitude = getValue(arguments[currentArgument], ArgumentType.Longitude);

                    if (arguments.length > currentArgument + 1 && longitude.equals("")) {
                        currentArgument++;
                        longitude = arguments[currentArgument];
                    }
                }
            } else if (sensoridPattern.matcher(arguments[currentArgument]).matches()) {   // Case "--sensorid 5"
                mode = AirlyMode.Sensor;
                sensorid = getValue(arguments[currentArgument], ArgumentType.SensorID);
                if (sensorid.equals("")) {
                    currentArgument++;
                    sensorid = arguments[currentArgument];
                }
            } else if (longitudePattern.matcher(arguments[currentArgument]).matches()) {     // Case "--longitude=5 --latitude=9"
                longitude = getValue(arguments[currentArgument], ArgumentType.Longitude);
                mode = AirlyMode.Coordinates;

                if (longitude.equals("")) {
                    currentArgument++;
                    longitude = arguments[currentArgument];
                }

                currentArgument++;
                if (latitudePattern.matcher(arguments[currentArgument]).matches()) {

                    latitude = getValue(arguments[currentArgument], ArgumentType.Latitude);

                    if (arguments.length > currentArgument + 1 && latitude.equals("")) {
                        currentArgument++;
                        latitude = arguments[currentArgument];
                    }
                }
            }
        }

        if (arguments.length > currentArgument + 1 && apiKeyPattern.matcher(arguments[currentArgument + 1]).matches()) {
            currentArgument++;

            apikey = getValue(arguments[currentArgument], ArgumentType.Apikey);

            if (arguments.length > currentArgument + 1 && apikey.equals("")) {
                currentArgument++;
                apikey = arguments[currentArgument];
            }
        }

        currentArgument++;
        if (arguments.length > currentArgument && historyPattern.matcher(arguments[currentArgument]).matches()) {
            history = true;
        }

        boolean noCoordinates = (latitude.equals("") || longitude.equals(""));
        boolean noSensorid = (sensorid.equals(""));

        if ((mode == AirlyMode.Coordinates && noCoordinates) ||
                (mode == AirlyMode.Sensor && noSensorid) || mode == null)
            throw new IllegalArgumentException(printHelp());

    }

    private String getValue(String argument, ArgumentType type) {

        Pattern latitudePattern = Pattern.compile("--latitude=?-?");
        Pattern longitudePattern = Pattern.compile("--longitude=?-?");
        Pattern sensoridPattern = Pattern.compile("--sensor-id=?");
        Pattern apiKeyPattern = Pattern.compile("--api-key=?");


        switch (type) {
            case SensorID:
                if (sensoridPattern.split(argument).length == 0) return "";
                return sensoridPattern.split(argument)[1];
            case Latitude:
                if (latitudePattern.split(argument).length == 0) return "";
                return latitudePattern.split(argument)[1];
            case Longitude:
                if (longitudePattern.split(argument).length == 0) return "";
                return longitudePattern.split(argument)[1];
            case Apikey:
                if (apiKeyPattern.split(argument).length == 0) return "";
                return apiKeyPattern.split(argument)[1];
            default:
                return "";
        }
    }

    public boolean includeHistory() {
        return history;
    }

    public boolean setApikey(String apk) {
        if (apikey == null || apikey.equals("")) {
            hasAPIkey = true;
            apikey = apk;
            return true;
        }

        return false;
    }

    public String generateURL() {
        String basic = "https://airapi.airly.eu/v1/";

        if (mode == AirlyMode.Sensor) basic += "sensor/";
        else basic += "mapPoint/";

        basic += "measurements?";

        if (mode == AirlyMode.Sensor) basic += "sensorId=" + sensorid;
        else basic += "latitude=" + latitude + "&longitude=" + longitude;

        basic += "&apikey=" + apikey;

        return basic;
    }

    public String printHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("Wrong arguments, acceptable forms:\n");
        builder.append("--sensor-id=123\n");
        builder.append("--sensor-id 123\n");
        builder.append("--sensor-id=123 --api-key=123abc [--history]\n");
        builder.append("--sensor-id 123 --api-key 123abc [--history]\n");
        builder.append("--latitude=50.06 --longitude=19.93 --api-key=123abc [--history]\n");
        builder.append("--latitude 50.06 --longitude 19.93 --api-key 123abc [--history]\n and so on..\n");
        builder.append("You also have to include api-key either in arguments or in the environmental variable (API_KEY or api_key)\n");
        return builder.toString();
    }
}


enum ArgumentType {
    SensorID,
    Latitude,
    Longitude,
    Apikey
}