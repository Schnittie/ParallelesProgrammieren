package de.dhbw.parprog;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class AccountStateTestsFabian {
    Bank bank;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setupTest() {
        bank = new Bank();
    }

    @Test
    public void newAccountHasNoCredits() {
        Account a = bank.createAccount();
        assertThat(bank.getBalance(a)).isEqualTo(0);
    }

    @Test
    public void canDepositAndWithdraw() throws IllegalAccountStateException {
        Account a = bank.createAccount();
        bank.deposit(a, 10);
        bank.withdraw(a, 5);
    }

    @Test
    public void cantDepositMoreThanMaximum() throws IllegalAccountStateException {
        Account a = bank.createAccount();
        bank.deposit(a, Account.UPPER_LIMIT);
        thrown.expect(IllegalAccountStateException.class);
        bank.deposit(a, 1);
        Assert.fail("Should have caused an exception");
    }

    @Test
    public void cantWithdrawBelowMinimum() throws IllegalAccountStateException {
        Account a = bank.createAccount();
        thrown.expect(IllegalAccountStateException.class);
        bank.withdraw(a, 1);
        Assert.fail("Should have caused an exception");
    }

    @Test
    public void canTransferBetweenAccounts() throws IllegalAccountStateException {
        Account a = bank.createAccount();
        Account b = bank.createAccount();
        bank.deposit(a, 50);
        bank.transfer(a, b, 25);
        assertThat(bank.getBalance(a)).isEqualTo(25);
        assertThat(bank.getBalance(b)).isEqualTo(25);
    }

    @Test
    public void cantTransferMoreThanAvailable() throws IllegalAccountStateException {
        Account a = bank.createAccount();
        Account b = bank.createAccount();
        bank.deposit(a, 25);
        thrown.expect(IllegalAccountStateException.class);
        bank.transfer(a, b, 50);
    }

    @Test
    public void concurrentDepositsShouldNotCauseIssues() throws InterruptedException {
        Account a = bank.createAccount();
        int threads = 10;
        long depositAmount = 1000;
        Thread[] threadArray = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() -> {
                try {
                    bank.deposit(a, depositAmount);
                } catch (IllegalAccountStateException e) {
                    e.printStackTrace();
                }
            });
            threadArray[i].start();
        }

        for (int i = 0; i < threads; i++) {
            threadArray[i].join();
        }

        assertThat(bank.getBalance(a)).isEqualTo(threads * depositAmount);
    }

    @Test
    public void concurrentTransfersShouldNotCauseIssues() throws InterruptedException {
        Account a = bank.createAccount();
        Account b = bank.createAccount();
        int threads = 10;
        long depositAmount = 1000;
        bank.deposit(a, threads * depositAmount);

        Thread[] threadArray = new Thread[threads];

        for (int i = 0; i < threads; i++) {
            threadArray[i] = new Thread(() -> {
                try {
                    bank.transfer(a, b, depositAmount);
                } catch (IllegalAccountStateException e) {
                    e.printStackTrace();
                }
            });
            threadArray[i].start();
        }

        for (int i = 0; i < threads; i++) {
            threadArray[i].join();
        }

        assertThat(bank.getBalance(a)).isEqualTo(0);
        assertThat(bank.getBalance(b)).isEqualTo(threads * depositAmount);
    }

}
