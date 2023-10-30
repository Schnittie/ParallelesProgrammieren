package de.dhbw.parprog;

import java.util.concurrent.Flow;

public class TotalCrawler implements Flow.Subscriber<Person> {
    int number = 0;
    Flow.Subscription subscription;


    public int getNumber() {
        return number;
    }


    @Override
    public void onSubscribe(Flow.Subscription sub) {
        this.subscription = sub;
        subscription.request(1);
    }

    @Override
    public void onNext(Person item) {
        number++;
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("die");
    }

    @Override
    public void onComplete() {
        System.out.println(" number of students : "+getNumber());
    }
}
