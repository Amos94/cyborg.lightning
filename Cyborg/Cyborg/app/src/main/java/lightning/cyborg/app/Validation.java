package lightning.cyborg.app;

public class Validation {
    public String getValidMessage(String sin){
        return sin.replaceAll("\\\\", "").replaceAll(";", "");
    }

    public String getValidInterest(String sin){
        return getValidMessage(sin).toLowerCase().replaceAll(", ", ",").replaceAll(" ,", ",");
    }
}
