// Abstract Class with Static Block

abstract class Person {

    private String name;
    private int age;

    static String bankName;

    // Static Block
    static {
        bankName = "SRITW Bank";
        System.out.println("Static block in Person executed. Bank name set.");
    }

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Encapsulation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract String getDetails();
}


// Composition Example

class Address {

    String city;
    String state;
    String pincode;

    public Address(String city, String state, String pincode) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return city + ", " + state + " - " + pincode;
    }
}


// Data Class with Inner Class

class BankAccount extends Person {

    private String accountNo;
    private String accountType;
    private double balance;
    private Address address;

    // Inner Class
    class Transaction {

        double amount;
        String type;

        public Transaction(double amount, String type) {
            this.amount = amount;
            this.type = type;
        }

        public void showTransaction() {
            System.out.println(
                "Transaction: " + type +
                ", Amount: " + amount
            );
        }
    }


    // Constructor

    public BankAccount(
            String name,
            int age,
            String accountNo,
            String accountType,
            double balance,
            Address address) {

        super(name, age);

        this.accountNo = accountNo;
        this.accountType = accountType;
        this.balance = balance;
        this.address = address;
    }


    // Copy Constructor

    public BankAccount(BankAccount b) {

        super(b.getName(), b.getAge());

        this.accountNo = b.accountNo;
        this.accountType = b.accountType;
        this.balance = b.balance;
        this.address = b.address;
    }


    // Deposit

    public void deposit(double amount)
            throws InvalidBankDataException {

        if (amount <= 0) {
            throw new InvalidBankDataException(
                "Invalid deposit amount"
            );
        }

        balance += amount;

        System.out.println(
            "Amount deposited: " + amount
        );
    }


    // Withdraw

    public void withdraw(double amount)
            throws InvalidBankDataException {

        if (amount <= 0) {
            throw new InvalidBankDataException(
                "Invalid withdrawal amount"
            );
        }

        if (amount > balance) {
            throw new InvalidBankDataException(
                "Insufficient balance"
            );
        }

        balance -= amount;

        System.out.println(
            "Amount withdrawn: " + amount
        );
    }


    // Getter

    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }


    // Method Overriding

    @Override
    public String getDetails() {

        return "Customer: " + getName()
                + ", Age: " + getAge()
                + ", Account No: " + accountNo
                + ", Account Type: " + accountType
                + ", Balance: " + balance
                + ", Address: " + address;
    }


    @Override
    public String toString() {
        return getDetails();
    }


    // equals()

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof BankAccount))
            return false;

        BankAccount b = (BankAccount) obj;

        return accountNo.equals(b.accountNo);
    }
}


// Interfaces

interface ATMActivities {

    void withdrawFromATM(double amount)
            throws InvalidBankDataException;
}


interface OnlineBankingActivities {

    void transferMoney(
            BankAccount account,
            double amount)
            throws InvalidBankDataException;
}


// Database Interface

interface DatabaseOperations {

    void insert(BankAccount b)
            throws InvalidBankDataException;

    void update(BankAccount b);

    void delete(String accountNo);

    BankAccount fetch(String accountNo);


    // Default Interface Method

    default void log(String msg) {

        System.out.println("LOG: " + msg);
    }
}


// Inheritance + Multiple Interfaces

class SavingsAccount extends BankAccount
        implements ATMActivities, OnlineBankingActivities {

    private double interestRate;


    public SavingsAccount(
            String name,
            int age,
            String accountNo,
            String accountType,
            double balance,
            Address address,
            double interestRate) {

        super(
            name,
            age,
            accountNo,
            accountType,
            balance,
            address
        );

        this.interestRate = interestRate;
    }


    // Method Overriding

    @Override
    public String getDetails() {

        return super.getDetails()
                + ", Interest Rate: "
                + interestRate + "%";
    }


    // ATM Interface

    @Override
    public void withdrawFromATM(double amount)
            throws InvalidBankDataException {

        System.out.println(
            "ATM withdrawal started..."
        );

        withdraw(amount);
    }


    // Online Banking Interface

    @Override
    public void transferMoney(
            BankAccount account,
            double amount)
            throws InvalidBankDataException {

        withdraw(amount);

        account.deposit(amount);

        System.out.println(
            "Money transferred successfully."
        );
    }
}


// Business Class

class BankManager implements DatabaseOperations {

    private static int accountCount = 0;

    public static final String BANK_CODE = "SRITW001";


    // Method Overloading

    public void insert(
            String name,
            int age,
            String accountNo,
            String accountType,
            double balance,
            Address address)
            throws InvalidBankDataException {

        insert(
            new BankAccount(
                name,
                age,
                accountNo,
                accountType,
                balance,
                address
            )
        );
    }


    // Interface Implementation

    @Override
    public void insert(BankAccount b)
            throws InvalidBankDataException {

        if (b == null) {

            throw new InvalidBankDataException(
                "Invalid bank account data"
            );
        }

        accountCount++;

        log(
            "Inserted account: "
            + b.getAccountNo()
        );
    }


    @Override
    public void update(BankAccount b) {

        log(
            "Updated account: "
            + b.getAccountNo()
        );
    }


    @Override
    public void delete(String accountNo) {

        log(
            "Deleted account: "
            + accountNo
        );
    }


    @Override
    public BankAccount fetch(String accountNo) {

        return new BankAccount(
            "Dummy",
            25,
            accountNo,
            "Savings",
            10000,
            new Address(
                "Warangal",
                "TG",
                "506001"
            )
        );
    }


    // Static Method

    public static int getAccountCount() {

        return accountCount;
    }
}


// Custom Exception

class InvalidBankDataException
        extends Exception {

    public InvalidBankDataException(
            String message) {

        super(message);
    }
}


// Driver Class

public class MainApp {

    public static void main(String[] args) {

        // Composition
        Address addr =
            new Address(
                "Warangal",
                "TG",
                "506001"
            );


        // Creating Savings Account

        SavingsAccount sa =
            new SavingsAccount(
                "John",
                22,
                "A001",
                "Savings",
                10000,
                addr,
                5.5
            );


        // Creating another account

        BankAccount account2 =
            new BankAccount(
                "Rahul",
                25,
                "A002",
                "Savings",
                5000,
                addr
            );


        // Manager Object

        BankManager manager =
            new BankManager();


        try {

            // Insert account

            manager.insert(sa);


            // Deposit

            sa.deposit(2000);


            // ATM Withdrawal

            sa.withdrawFromATM(1000);


            // Transfer money

            sa.transferMoney(
                account2,
                2000
            );


            // Inner Class

            BankAccount.Transaction transaction =
                sa.new Transaction(
                    2000,
                    "Transfer"
                );

            transaction.showTransaction();


            // Polymorphism

            BankAccount account = sa;

            System.out.println(
                account.getDetails()
            );


            // Static Method

            System.out.println(
                "Total Accounts: "
                + BankManager.getAccountCount()
            );

        }
        catch (InvalidBankDataException e) {

            System.out.println(
                "Error: "
                + e.getMessage()
            );
        }
    }
}