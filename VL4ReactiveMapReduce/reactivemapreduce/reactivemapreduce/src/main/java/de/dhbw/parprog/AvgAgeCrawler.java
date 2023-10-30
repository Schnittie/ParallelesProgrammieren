package de.dhbw.parprog;

import java.util.concurrent.Flow;

public class AvgAgeCrawler implements Flow.Subscriber<Person> {
    int number = 0;
    int totalAge =0;
    Flow.Subscription subscription;

    public float getAvgAge() {
        return (float) totalAge /number;
    }

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
        totalAge += item.age;
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("die");
    }

    @Override
    public void onComplete() {
        System.out.println(" durschnittlicher alter : "+getAvgAge());
    }
}
