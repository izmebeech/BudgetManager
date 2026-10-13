import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BudgetManager {

    static void main() {
        Repository<Transaction> repository = new Repository<>();
        Calculator calculator = new Calculator(repository);

            while (true) {
                IO.println("~~~~~~~~~~~~~~~~~~~~");
                IO.println("  💅Budget Manager💅");
                IO.println("~~~~~~~~~~~~~~~~~~~~");
                IO.println("1. 💵Add transaction");
                IO.println("2. 🧾Transaction history");
                IO.println("3. ⚖️Balance & category summary");
                IO.println("4. 📚Filter transactions");
                IO.println("5. 💾Save transactions to file");
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

                        boolean validType = true;     // users choice is valid until we discover otherwise
                        TransactionType type = null; //creates variable to store transaction type, null bc no value yet
                        switch (whatType) {
                            case "1":
                                type = TransactionType.INCOME;
                                break;
                            case "2":
                                type = TransactionType.EXPENSE;
                                break;

                            default: // Runs if the user enters anything other than 1 or 2.
                                IO.println("Please, choose option 1 or 2.");
                                validType = false;
                        }
                        if (!validType) {// If the choice was invalid, stop case and return to menu.
                            break;
                        }

                        IO.println("Category of transaction:");
                        String whatCategory = IO.readln();
                        IO.println("Amount:");
                        String whatAmount = IO.readln();
                        double amount = Double.parseDouble(whatAmount);     //converts user input string into double
                        LocalDateTime date = LocalDateTime.now();

                        Transaction transaction = new Transaction(    //creates new transaction based on user input
                                date, whatCategory, amount, type);

                        repository.add(transaction);


                        break;  // Exit case and let main menu loop repeat.

                    case "2":
                        // View transaction history
                        List<Transaction> listOfTransactions = repository.findAll();
                        if (repository.findAll().isEmpty()) {
                            IO.println("No transactions found.");
                        } else
                            for (Transaction currentTransaction : listOfTransactions) { //loops through all transactions
                                IO.println(currentTransaction);
                            }
                        break;

                    case "3":
                        // View balance and category summary
                        if (repository.findAll().isEmpty()) {
                            IO.println("No transactions found. Must add transaction to view budget summary.");
                            break;
                        }
                        double totalIncome = calculator.calculateTotalIncome();
                        double totalSpending = calculator.calculateTotalSpending();
                        double balance = calculator.calculateBalance();

                        IO.println("Total income: " + totalIncome);
                        IO.println("Total spending: " + totalSpending);
                        IO.println("Current balance: " + balance);

                        Map<String, Double> categoryTotals = calculator.calculateTotalsByCategory();

                        IO.println("Category summary:");
                        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) { //shows each category and its amount
                            IO.println(entry.getKey() + ": " + entry.getValue()); //gets category name(key) and total for said category

                        }
                        if (totalSpending > totalIncome) {
                            IO.println("Warning!🚨 Your spending exceeds your income. Consider slowing down your spending.");
                        }
                        break;


                    case "4":
                        // Filter transactions
                        IO.println("Filter transactions:");
                        IO.println();
                        IO.println("1. By transaction type");
                        IO.println("2. By category");
                        IO.println();
                        IO.println("Press Enter to go back.");

                        String filterChoice = IO.readln();

                        if (filterChoice.isBlank()) {
                            break; //return to main menu if user chooses to back
                        }

                        switch (filterChoice) {
                            case "1":
                                // Display transactions grouped by type.
                                List<Transaction> incomes = repository.findWhere(
                                        incomeTransaction ->
                                                incomeTransaction.type() == TransactionType.INCOME
                                );

                                List<Transaction> expenses = repository.findWhere(
                                        expenseTransaction ->
                                                expenseTransaction.type() == TransactionType.EXPENSE
                                );

                                IO.println("=== INCOME ===");
                                for (Transaction incomeTransaction : incomes) {
                                    IO.println(incomeTransaction);
                                }

                                IO.println("=== EXPENSES ===");
                                for (Transaction expenseTransaction : expenses) {
                                    IO.println(expenseTransaction);
                                }

                                break;

                            case "2":
                                // Filter by category, with case-insensitive input
                                IO.println("Enter category: ");
                                IO.println("Press Enter to go back.");

                                String selectedCategory = IO.readln();

                                if (selectedCategory.isBlank()) {
                                    break;
                                }
                                List<Transaction> categoryMatches = repository.findWhere( //
                                        categoryTransaction ->
                                                categoryTransaction.category().equalsIgnoreCase(selectedCategory));
                                if (categoryMatches.isEmpty()) {
                                    IO.println("No transactions found for category: " + selectedCategory);
                                } else {
                                    for (Transaction categoryTransaction : categoryMatches) {
                                        IO.println(categoryTransaction);
                                    }
                                }
                                break;

                            default:
                                IO.println("Invalid option. Please choose 1 or 2.");
                        }
                        break;



                        case "5":
                            // Save transactions to file
                            break;
                    }
                }
            }
        }
