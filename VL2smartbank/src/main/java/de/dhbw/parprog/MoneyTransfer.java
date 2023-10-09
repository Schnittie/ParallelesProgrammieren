package de.dhbw.parprog;

public class MoneyTransfer implements Runnable{
    Account fromAccount, toAccount;
    long amount;
    int count;
    Bank bank;

    public MoneyTransfer(Account fromAccount, Account toAccount, long amount, int count, Bank bank) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.count = count;
        this.bank = bank;
    }
    public static void transfer(Account fromAccount, Account toAccount, long amount, Bank bank){
        bank.transfer(fromAccount,toAccount,amount);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            transfer(fromAccount,toAccount,amount,bank);
        }
    }
}
