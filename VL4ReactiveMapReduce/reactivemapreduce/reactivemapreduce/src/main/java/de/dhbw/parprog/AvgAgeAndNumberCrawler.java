package de.dhbw.parprog;

import java.util.concurrent.Flow;

public class AvgAgeAndNumberCrawler implements Flow.Subscriber<Person> {
    int number = 0;
    int maxName = 0;
    int totalAge =0;
    Flow.Subscription subscription;

    public float getAvgAge() {
        return (float) totalAge /number;
    }

    public int getNumber() {
        return number;
    }

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
        number++;
        totalAge += item.age;
        System.out.println(" durschnittlicher alter : "+getAvgAge()+" number of students : "+getNumber()+" längster name : "+getMaxName());
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("die");
    }

    @Override
    public void onComplete() {
        System.out.println(" durschnittlicher alter : "+getAvgAge()+" number of students : "+getNumber()+" längster name : "+getMaxName());
    }
}
