public class AfricanParrot extends Parrot {

    private final double loadFactor = 9.0;
    private final double minBasedSpped = 0.0;

    private double voltage;
    private int numberOfCoconuts;

    public AfricanParrot(int numberOfCoconuts, double voltage) {
        this.voltage = voltage;
        this.numberOfCoconuts = numberOfCoconuts;
    }


    @Override
    public double getSpeed() {
        return Math.max(getMinBasedSpeed(), super.getBaseSpeed() - getLoadFactor() * numberOfCoconuts);
    }


    double getLoadFactor() {
        return this.loadFactor;
    }

    double getMinBasedSpeed(){
        return minBasedSpped;
    }
}
