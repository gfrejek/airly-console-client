import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeasurementBox {

    private String fromDateTime;
    private String tillDateTime;
    Measurement measurements;

    public String getFromDate() {
        return parseDate(fromDateTime);
    }

    public String getTillDate() {
        return parseDate(tillDateTime);
    }

    public String getFromHour() {
        return parseHour(fromDateTime);
    }

    public String getTillHour() {
        return parseHour(tillDateTime);
    }

    public String parseDate(String input) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");
        Date date;

        try {
            date = dateParser.parse(input);
        } catch (ParseException ex) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    public String parseHour(String input) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");
        Date date;

        try {
            date = dateParser.parse(input);
        } catch (ParseException ex) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    public Measurement getMeasurements() {
        return measurements;
    }
}
