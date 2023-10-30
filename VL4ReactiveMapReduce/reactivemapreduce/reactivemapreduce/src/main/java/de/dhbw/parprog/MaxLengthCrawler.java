package de.dhbw.parprog;

import java.util.concurrent.Flow;

public class MaxLengthCrawler implements Flow.Subscriber<Person> {
    int maxName = 0;
    Flow.Subscription subscription;



    public int getMaxName() {
        return maxName;
    }

    @Override
    public void onSubscribe(Flow.Subscription sub) {
        this.subscription = sub;
        subscription.request(1);
    }

    @Override
    public void onNext(Person item) {
        maxName = Math.max(item.surname.length(), maxName);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("die");
    }

    @Override
    public void onComplete() {
        System.out.println(" längster name : "+getMaxName());
    }
}
