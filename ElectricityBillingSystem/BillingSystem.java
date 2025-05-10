import java.io.*;
import java.util.*;

public class BillingSystem {

    private final String USERS_FILE = "Data/users.txt";
    private final String CUSTOMERS_FILE = "Data/customers.txt";
    private final String BILLS_DIR = "Data/bills/";

    public User login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return new User(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Login file error.");
        }
        return null;
    }

    public void addCustomer(Scanner scanner) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE, true))) {
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine();
            System.out.print("Enter meter number: ");
            String meter = scanner.nextLine();

            bw.write(name + "," + meter);
            bw.newLine();
            System.out.println("Customer added.");
        } catch (IOException e) {
            System.out.println("Error adding customer.");
        }
    }

    public void viewCustomers() {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            System.out.println("\n--- Customers ---");
            String line;
            while ((line = br.readLine()) != null) {
                Customer c = Customer.fromString(line);
                System.out.println("Name: " + c.getName() + ", Meter: " + c.getMeterNumber());
            }
        } catch (IOException e) {
            System.out.println("Error reading customers.");
        }
    }

    public void deleteCustomer(Scanner scanner) {
        System.out.print("Enter meter number to delete: ");
        String meterToDelete = scanner.nextLine();
        List<String> updated = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains(meterToDelete)) {
                    updated.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (String line : updated) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Customer deleted.");
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    public void generateBill(Scanner scanner) {
        System.out.print("Enter meter number: ");
        String meter = scanner.nextLine();
        System.out.print("Enter units consumed: ");
        int units = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        double billAmount = calculateBill(units);
        String filename = BILLS_DIR + meter + "_bill.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("Meter No: " + meter);
            bw.newLine();
            bw.write("Units Consumed: " + units);
            bw.newLine();
            bw.write("Bill Amount: ₹" + billAmount);
            bw.newLine();
            bw.write("Date: " + new Date());
            System.out.println("Bill generated at: " + filename);
        } catch (IOException e) {
            System.out.println("Error generating bill.");
        }
    }

    private double calculateBill(int units) {
        double rate;
        if (units <= 100) rate = 5;
        else if (units <= 300) rate = 7;
        else rate = 10;
        return units * rate;
    }
}

