import java.util.Arrays;

public class MeasurementJSON {

    private Measurement currentMeasurements;
    private MeasurementBox[] history;
    private MeasurementBox[] forecast;

    @Override
    public String toString() {
        return "MeasurementJSON{" +
                "currentMeasurement=" + currentMeasurements +
                ", history=" + Arrays.toString(history) +
                ", forecast=" + Arrays.toString(forecast) +
                '}';
    }

    public Measurement getCurrentMeasurements() {
        return currentMeasurements;
    }

    public MeasurementBox[] getHistory() {
        return history;
    }

    public MeasurementBox[] getForecast() {
        return forecast;
    }
}
