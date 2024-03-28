/**
 * PS4: Class that can hold both string & double, for purpose of averageSeparation
 * Author: Ryan Lee, CS10, Winter 2022
 */

public class StringDouble {
    protected String string;
    protected double Double;
    public StringDouble(String string, double Double){ //can hold double & string
        this.string = string;
        this.Double = Double;
    }
    public double getDouble() {
        return Double;
    }
    public String getString() {
        return string;
    }
}
