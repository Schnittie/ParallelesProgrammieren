package de.dhbw.parprog;

public class Person {
    public final String givenName, surname;
    public final int age;
    public final boolean isStudent;

    public Person(String givenName, String surname, int age, boolean isStudent) {
        this.surname = surname;
        this.givenName = givenName;
        this.age = age;
        this.isStudent = isStudent;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + surname + '\'' +
                ", vorname='" + givenName + '\'' +
                ", alter=" + age +
                ", isStudent=" + isStudent +
                '}';
    }
}
