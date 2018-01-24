
public class Measurement {

    private double airQualityIndex;
    private double pm1;
    private double pm25;
    private double pm10;
    private double pressure;
    private double humidity;
    private double temperature;
    private double pollutionLevel;

    public double[] getDataPack() {
        double[] result = new double[7];
        result[0] = airQualityIndex;
        result[1] = pm25;
        result[2] = pm10;
        result[3] = pressure / 100;
        result[4] = humidity;
        result[5] = temperature;
        result[6] = pollutionLevel;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "airQualityIndex=" + airQualityIndex +
                ", pm1=" + pm1 +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                ", pollutionLevel=" + pollutionLevel +
                '}';
    }

    public boolean isNull() {
        return (pressure == 0);
    }
}
