import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            OptionParser op = new OptionParser(args);
            op.checkArguments();

            op.setApikey(System.getenv("API_KEY"));

            ParserAPI parserAPI = new ParserAPI(op.generateURL());

            DataPresenter dataPresenter = new DataPresenter(parserAPI.downloadJSON(), op.includeHistory());

            System.out.println(dataPresenter.printData());

        } catch (IOException | IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }
}
