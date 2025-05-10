import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Electricity Billing System ===");

        try (Scanner scanner = new Scanner(System.in)) {
            BillingSystem billingSystem = new BillingSystem();

            // Login
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = billingSystem.login(username, password);

            if (user == null) {
                System.out.println("❌ Invalid credentials. Exiting...");
                return;
            }

            System.out.println("✅ Login successful. Role: " + user.getRole());

            boolean running = true;
            while (running) {
                System.out.println("\n===== Main Menu =====");
                System.out.println("1. Add Customer");
                System.out.println("2. View Customers");
                System.out.println("3. Delete Customer");
                System.out.println("4. Generate Bill");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> billingSystem.addCustomer(scanner);
                    case 2 -> billingSystem.viewCustomers();
                    case 3 -> billingSystem.deleteCustomer(scanner);
                    case 4 -> billingSystem.generateBill(scanner);
                    case 5 -> {
                        System.out.println("Exiting the system. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("❗ Invalid option. Please try again.");
                }
            }
        }
    }
}
