package de.dhbw.parprog;


public class Account {
    public static long LOWER_LIMIT = 0;
    public static long UPPER_LIMIT = 100000;


    private final long id;
    private long cashMoney = 0;

    public Account() {
        this.id = System.nanoTime();
    }

    public Long getId() {
        return id;
    }

    public synchronized void transaction(long amount){
        cashMoney = cashMoney + amount;
        if (cashMoney< LOWER_LIMIT || cashMoney > UPPER_LIMIT) throw new IllegalAccountStateException();
    }

    public synchronized long getMoney(){
        return cashMoney;
    }

    // TODO: Mit eigener Implementierung füllen
}
