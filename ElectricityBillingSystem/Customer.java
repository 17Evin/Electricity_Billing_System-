public class Customer {
    private final String name;
    private final String meterNumber;

    public Customer(String name, String meterNumber) {
        this.name = name;
        this.meterNumber = meterNumber;
    }

    public String getName() {
        return name;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    @Override
    public String toString() {
        return name + "," + meterNumber;
    }

    public static Customer fromString(String line) {
        String[] parts = line.split(",");
        return new Customer(parts[0], parts[1]);
    }
}
