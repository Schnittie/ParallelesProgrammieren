package de.dhbw.parprog;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        Account accountA = bank.createAccount();
        Account accountB = bank.createAccount();
        bank.deposit(accountA,10000);
        bank.deposit(accountB,10000);
        System.out.println("Start balance: A="+accountA.getMoney() + " , B="+ accountB.getMoney());

        Thread t1 = new Thread(new MoneyTransfer(accountA,accountB,10,1000,bank));
        Thread t2 = new Thread(new MoneyTransfer(accountB,accountA,10,1000,bank));
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Start balance: A="+accountA.getMoney() + " , B="+ accountB.getMoney());
    }
}
