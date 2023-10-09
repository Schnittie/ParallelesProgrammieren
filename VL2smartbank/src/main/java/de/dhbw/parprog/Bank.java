package de.dhbw.parprog;

import org.apache.commons.lang.NotImplementedException;


public class Bank {
    /**
     * Erzeugt ein neues Konto mit initialem Kontostand 0.
     * @return das neue Konto
     */
	public synchronized Account createAccount() {
        return new Account();
    }

    /**
     * Ruft den aktuellen Kontostand ab
     * @param account Konto, dessen Kontostand bestimmt werden soll
     * @return der aktuelle Kontostand
     */
	public long getBalance(Account account) throws IllegalArgumentException {
        return account.getMoney();
    }

    /**
     * Einzahlen eines bestimmten Betrags
     * @param account das Konto, auf den der Betrag eingezahlt werden soll
     * @param amount der Betrag (muß >=0 sein)
     * @throws IllegalArgumentException bei ungültigen Parametern
     * @throws IllegalAccountStateException falls der Kontostand außerhalb des gültigen Wertebereichs geraten würde
     */
	public void deposit(Account account, long amount) throws IllegalAccountStateException, IllegalArgumentException {
        if (amount<=0) throw new IllegalArgumentException();
        synchronized (account.getId()){
            if (account.getMoney() + amount > Account.UPPER_LIMIT) throw new IllegalAccountStateException();
            account.transaction(amount);
        }
    }

    /**
     * Abheben eines bestimmten Betrags
     * @param account das Konto, von dem der Betrag abgehoben werden soll
     * @param amount der Betrag (muß >=0 sein)
     * @throws IllegalArgumentException bei ungültigen Parametern
     * @throws IllegalAccountStateException falls der Kontostand außerhalb des gültigen Wertebereichs geraten würde
     */
	public void withdraw(Account account, long amount) throws IllegalAccountStateException, IllegalArgumentException {
        if (amount<=0) throw new IllegalArgumentException();
        synchronized (account.getId()){
            if (account.getMoney() - amount < Account.LOWER_LIMIT) throw new IllegalAccountStateException();
            account.transaction(-amount);
        }
    }

    /**
     * Überweisen eines Betrags von einem Konto auf ein anderes
     * @param fromAccount Konto, von dem abgebucht werden soll
     * @param toAccount Konto, auf das gutgeschrieben werden soll
     * @param amount der zu transferierende Betrag (muß >=0 sein)
     * @throws IllegalArgumentException bei ungültigen Parametern
     * @throws IllegalAccountStateException falls einer der Kontostände außerhalb des gültigen Wertebereichs geraten würde
     */
	public void transfer(Account fromAccount, Account toAccount, long amount) throws IllegalAccountStateException, IllegalArgumentException {
        if (amount<=0) throw new IllegalArgumentException();
        if (fromAccount.getId() == toAccount.getId()) throw new IllegalArgumentException();
        Account firstAccount = fromAccount.getId() > toAccount.getId()? fromAccount : toAccount;
        Account secondAccount = fromAccount.getId() < toAccount.getId()? fromAccount : toAccount;
        synchronized (firstAccount.getId()){
            synchronized (secondAccount.getId()){
                if (fromAccount.getMoney() - amount < Account.LOWER_LIMIT) throw new IllegalAccountStateException();
                if (toAccount.getMoney() + amount > Account.UPPER_LIMIT) throw new IllegalAccountStateException();
                fromAccount.transaction(-amount);
                toAccount.transaction(+amount);
            }
        }
    }
}
