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
                        IO.println();
                        IO.println("1. Income");
                        IO.println("2. Expense");
                        IO.println();
                        IO.println("Choose your option: ");
                        String whatType = IO.readln();

                        boolean validType = true;
                        TransactionType type = null;

                        switch (whatType) {
                            case "1":
                                type = TransactionType.INCOME;
                                break;
                            case "2":
                                type = TransactionType.EXPENSE;
                                break;
                            default:
                                IO.println("Please, choose option 1 or 2.");
                                validType = false;
                        }

                        if (!validType){
                            break;
                        }

                        IO.println("Category of transaction:");
                        String whatCategory = IO.readln().trim();

                        if (whatCategory.isEmpty()){
                            IO.println("Must enter category, please try again.");
                            break;
                        }

                        boolean validAmount =false;
                        double amount = 0;

                        while (!validAmount){
                            IO.println("Amount:");
                            String whatAmount = IO.readln();

                            //make sure user input can be only positive numbers
                            try {
                                amount = Double.parseDouble(whatAmount); //convert number into string
                                if (!Double.isFinite(amount) || amount <= 0) {
                                    IO.println("Amount added must be a positive number. Please try again.");
                                } else {
                                    validAmount = true;
                                }
                            } catch (NumberFormatException e) {
                                IO.println("Invalid amount. Please enter a positive number.");
                            }
                        }

                        LocalDateTime date = LocalDateTime.now();
                        Transaction transaction = new Transaction(
                                date, whatCategory, amount, type
                        );

                        repository.add(transaction);

                        IO.println("Transaction added! 🎉");
                        IO.println("Press Enter to return to the main menu.");
                        IO.readln();

                        break;


                    case "2":
                        // View transaction history
                        List<Transaction> listOfTransactions = repository.findAll();
                        if (listOfTransactions.isEmpty()) {
                            IO.println("No transactions found.");
                        } else
                            for (Transaction currentTransaction : listOfTransactions) { //loops through all transactions
                                IO.println(currentTransaction.toDisplayString());
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
                        while (true) {
                            IO.println("Filter transactions:");
                            IO.println();
                            IO.println("1. By transaction type");
                            IO.println("2. By category");
                            IO.println();
                            IO.println("Press Enter to return to the main menu.");

                            String filterChoice = IO.readln();

                            // Leave filter submenu and return to the main menu.
                            if (filterChoice.isBlank()) {
                                break;
                            }

                            switch (filterChoice) {
                                case "1":
                                    // Display transactions grouped by type.
                                    List<Transaction> incomes = repository.findWhere(
                                            incomeTransaction ->
                                                    incomeTransaction.type() == TransactionType.INCOME);

                                    List<Transaction> expenses = repository.findWhere(
                                            expenseTransaction ->
                                                    expenseTransaction.type() == TransactionType.EXPENSE);

                                    IO.println("=== INCOME ===");
                                    for (Transaction incomeTransaction : incomes) {
                                        IO.println(incomeTransaction.toDisplayString());
                                    }

                                    IO.println("=== EXPENSES ===");
                                    for (Transaction expenseTransaction : expenses) {
                                        IO.println(expenseTransaction.toDisplayString());
                                    }
                                    break;

                                case "2":
                                    // Filter transactions by category.
                                    IO.println("Enter category:");
                                    IO.println("Press Enter to return to filter menu.");

                                    String selectedCategory = IO.readln();

                                    // Return to the filter submenu if input is blank.
                                    if (selectedCategory.isBlank()) {
                                        break;
                                    }

                                    List<Transaction> categoryMatches = repository.findWhere(
                                            categoryTransaction ->
                                                    categoryTransaction.category()
                                                            .equalsIgnoreCase(selectedCategory));

                                    if (categoryMatches.isEmpty()) {
                                        IO.println("No transactions found for category: "
                                                + selectedCategory);
                                    } else {
                                        for (Transaction categoryTransaction : categoryMatches) {
                                            IO.println(categoryTransaction.toDisplayString());
                                        }
                                    }
                                    break;

                                default:
                                    IO.println("Invalid option. Please choose 1 or 2.");
                            }
                        }
                        break;



                        case "5":
                            // Save transactions to file
                            break;
                    }
                }
            }
        }
