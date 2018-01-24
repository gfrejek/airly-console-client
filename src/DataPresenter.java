import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPresenter {
    private MeasurementJSON jsonObject;
    private MeasurementBox[] history;
    private Measurement currentMeasurements;
    boolean includeHistory = false;


    public DataPresenter(MeasurementJSON json, boolean history) {
        this.jsonObject = json;
        this.history = jsonObject.getHistory();
        this.currentMeasurements = jsonObject.getCurrentMeasurements();
        this.includeHistory = history;
        if (currentMeasurements.isNull())
            throw new IllegalArgumentException("Niepoprawne koordynaty/sensorid - brak danych");
    }

    private String printSingleData(int hour) {       // hour == -1 means current measurement
        final String dateSpaces = "       ";
        final String hourSpaces = "        ";
        StringBuilder builder = new StringBuilder();
        Measurement measurements;
        MeasurementBox measurementBox;
        String fromDate;
        String fromHour;
        String tillHour = hourSpaces;

        if (hour == -1) {
            measurements = currentMeasurements;
            fromDate = getCurrentDate();
            fromHour = getCurrentHour();
        } else {
            measurementBox = history[hour];
            measurements = measurementBox.getMeasurements();
            fromDate = measurementBox.getFromDate();
            fromHour = measurementBox.getFromHour();
            tillHour = measurementBox.getTillHour();
        }

        double[] dataPack = measurements.getDataPack();

        builder.append("/------------------------\\\n");                 // top
        builder.append('|' + dateSpaces + fromDate + dateSpaces + "|\n");
        builder.append('|' + hourSpaces + fromHour + hourSpaces + "|\n");
        builder.append('|' + hourSpaces + tillHour + hourSpaces + "|\n");
        builder.append(String.format("|AirQualityIndex:%1s%7.2f|%n", " ", dataPack[0]));
        builder.append(String.format("|pm25(µg/m3):%1s%11.2f|%n", " ", dataPack[1]));
        builder.append(String.format("|pm10(µg/m3):%1s%11.2f|%n", " ", dataPack[2]));
        builder.append(String.format("|Pressure(hPa):%1s%9.2f|%n", " ", dataPack[3]));
        builder.append(String.format("|Humidity(%%):%1s%11.2f|%n", " ", dataPack[4]));
        builder.append(String.format("|Temperature(°C):%1s%7.2f|%n", " ", dataPack[5]));
        builder.append(String.format("|Pollution:%1s%13.2f|%n", " ", dataPack[6]));
        builder.append("\\------------------------/\n");              // bottom

        return builder.toString();
    }

    public String printData() {
        StringBuilder builder = new StringBuilder();
        int hourCounter = 0;
        builder.append(printSingleData(-1));


        if (includeHistory) {
            while (hourCounter < Integer.min(24, history.length)) {
                if (history[23 - hourCounter].getMeasurements().isNull()) break;
                builder.append("\n" + printSingleData(23 - hourCounter));
                hourCounter++;
            }
        }

        return builder.toString();
    }

    private String getCurrentDate() {
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(current);
    }

    private String getCurrentHour() {
        Date current = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(current);
    }
}
