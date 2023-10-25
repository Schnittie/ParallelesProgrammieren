package de.dhbw.parprog;

import java.util.Arrays;
import java.util.List;


public class PersonArchive {
    private static final List<Person> persons = Arrays.asList(
            new Person("Dirk", "Ostermann", 31, true),
            new Person("Marie", "Brandt", 55, false),
            new Person("Anja", "Baumgaertner", 72, false),
            new Person("Katharina", "Neustadt", 80, false),
            new Person("Felix", "Blau", 74, false),
            new Person("Maria", "Frueh", 49, false),
            new Person("Kevin", "Lemann", 28, true),
            new Person("Lucas", "Achen", 53, false),
            new Person("Bernd", "Schiffer", 35, false),
            new Person("Susanne", "Moeller", 23, true)
    );
    private static int personPtr = 0;

    public static Person getPerson() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) { }
        synchronized (persons) {
            if (personPtr < persons.size()) {
                Person result = persons.get(personPtr);
                personPtr++;
                return result;
            } else {
                return null;
            }
        }
    }
}
