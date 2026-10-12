import java.time.LocalDateTime;

public class BudgetManager {

    static void main() {
        Repository<Transaction> repository = new Repository<>();

            while (true) {
                IO.println("~~~~~~~~~~~~~~~~~~~~");
                IO.println("Budget Manager");
                IO.println("~~~~~~~~~~~~~~~~~~~~");
                IO.println("1. Add transaction");
                IO.println("2. Transaction history");
                IO.println("3. Balance & category summary");
                IO.println("4. Filter transactions");
                IO.println("5. Save transactions to file");
                IO.println("e. 🚪 Exit");
                IO.println("🔙Press enter to go back in the menu");
                IO.println();
                IO.print("Choose an option: ");
                String choice = IO.readln();

                if (choice.equals("e")) {
                    break;
                }

                switch (choice) {
                    case "1":
                        IO.print("Select transaction type:");
                        IO.println("1. Income");
                        IO.println("2. Expense");
                        IO.println("Choose your option: ");
                        String whatType = IO.readln();
                        boolean validType = true;
                        TransactionType type;
                        switch (whatType) {
                            case "1":
                                type = TransactionType.INCOME;
                                break;
                            case "2":
                                type = TransactionType.EXPENSE;
                                break;

                            default:
                                IO.println("Must choose option 1 or 2.");
                                validType = false;
                        }
                        if (!validType) {
                            break;
                        }

                        IO.println("Category of transaction:");
                        String whatCategory = IO.readln();
                        IO.println("Amount:");
                        String whatAmount = IO.readln();

                            break;
                        case "2":
                            // View transaction history
                            break;
                        case "3":
                            // View balance and category summary
                            break;
                        case "4":
                            // Filter transactions
                            break;
                        case "5":
                            // Save transactions to file
                            break;
                    }
                }
            }
        }
