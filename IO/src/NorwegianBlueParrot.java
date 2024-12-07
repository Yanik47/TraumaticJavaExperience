public class NorwegianBlueParrot extends Parrot {
    private boolean isNailed;
    private double voltage;
    private final double minStandardSpeep = 24.0;

    public NorwegianBlueParrot(double voltage, boolean isNailed) {
        this.voltage = voltage;
        this.isNailed = isNailed;
    }

    @Override
    public double getSpeed() {
        return (isNailed) ? 0 : getBaseSpeed(voltage);
    }

    double getBaseSpeed(double voltage) {
        return Math.min(getMinStandardSpeep(), voltage * super.getBaseSpeed());
    }


    double getMinStandardSpeep() {
        return this.minStandardSpeep;
    }
}
