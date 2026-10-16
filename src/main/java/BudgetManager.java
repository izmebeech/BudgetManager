import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
                        addTransaction(repository);

                        break;


                    case "2":
                        // View transaction history
                        showTransactionHistory(repository);
                        break;


                    case "3":
                        // View balance and category summary
                        balanceAndCategory(repository, calculator);
                        break;


                    case "4":
                        // Filter transactions
                        filterTransactions(repository);
                        break;



                        case "5":
                            // Save transactions to file
                            break;
                    }
                }
            }

    private static void filterTransactions(Repository<Transaction> repository) {
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
                    if (incomes.isEmpty()) {
                        IO.println("No income transactions found.");
                    } else {
                        for (Transaction incomeTransaction : incomes) {
                            IO.println(incomeTransaction.toDisplayString());
                        }
                    }
                    IO.println();

                    IO.println("=== EXPENSES ===");
                    if (expenses.isEmpty()) {
                        IO.println("No expense transactions found.");
                    } else {
                        for (Transaction expenseTransaction : expenses) {
                            IO.println(expenseTransaction.toDisplayString());
                        }
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
    }

    private static void addTransaction(Repository<Transaction> repository) {
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
                LOGGER.warning("Invalid transaction type selected.");
                IO.println("Please, choose option 1 or 2.");
                validType = false;

                // @TODO make sure program doesnt immediately loop back to menu, so user can read
        }

        if (!validType){
            return;
        }

        IO.println("Category of transaction:");
        String whatCategory = IO.readln().trim();

        if (whatCategory.isEmpty()){
            LOGGER.warning("No category selected, transaction rejected.");
            IO.println("Must enter category, please try again.");
            return;
        }

        boolean validAmount =false;
        double amount = 0;

        while (!validAmount){
            IO.println("Amount:");
            String whatAmount = IO.readln();

            //make sure user input is a positive number and not invalid characters
            try {
                amount = Double.parseDouble(whatAmount); //convert string into double
                if (!Double.isFinite(amount) || amount <= 0) {
                    LOGGER.warning("Invalid transaction amount: must be finite and greater than 0.");
                    IO.println("Amount added must be a greater than 0, please try again.");
                } else {
                    validAmount = true;
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("User entered an invalid transaction amount.");
                IO.println("Invalid amount. Use numbers only.");
            }
        }

        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction(
                date, whatCategory, amount, type
        );

        repository.add(transaction);
        LOGGER.info("Transaction added successfully.");

        IO.println("Transaction added! 🎉");
        IO.println("Press Enter to return to the main menu.");
        IO.readln();
    }

    private static void balanceAndCategory(Repository<Transaction> repository, Calculator calculator) {
        if (repository.findAll().isEmpty()) {
            IO.println("No transactions found. Must add transaction to view budget summary.");
            return;
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
            LOGGER.info("Total spending exceeds total income.");
            IO.println("Warning!🚨 Your spending exceeds your income. Consider slowing down your spending.");
        }
    }

    private static void showTransactionHistory(Repository<Transaction> repository) {
        List<Transaction> listOfTransactions = repository.findAll();
        if (listOfTransactions.isEmpty()) {
            IO.println("No transactions found.");
        } else
            for (Transaction currentTransaction : listOfTransactions) { //loops through all transactions
                IO.println(currentTransaction.toDisplayString());
            }
    }

    private static final Logger LOGGER =
            Logger.getLogger(BudgetManager.class.getName());


        }
