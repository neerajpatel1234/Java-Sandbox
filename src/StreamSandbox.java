import java.util.*;

/** --------------------------------------------- CLASSES --------------------------------------------- */

record Person (String name, int age){}

class Animal{
    String name;
    int age;
    public Animal(String name, int age){
        this.name = name;
        this.age = age;
    }
}

class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
    }
}


class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }
}

/** --------------------------------------------- Main Class --------------------------------------------- */
public class StreamSandbox {
    static List<Person> peopleList = List.of(
            new Person("John", 20),
            new Person("Mary", 25),
            new Person("Peter", 30),
            new Person("Neeraj", 100),
            new Person("Alex", 10),
            new Person("Alex", 10)
    );

    static List<Integer> listOfNumbers = List.of(1,2,3,4,5,6,7,8);
    static Map<Person, Integer> peopleMap = Map.of(
            new Person("John", 20), 1,
            new Person("Mary", 25), 2,
            new Person("Peter", 30), 3,
            new Person("Neeraj", 100), 4,
            new Person("Alex", 10), 5,
            new Person("York", 84), 6,
            new Person("Bob", 55), 7
    );

    static Set<Person> peopleSet = Set.of(
            new Person("John", 20),
            new Person("Mary", 25),
            new Person("Peter", 30),
            new Person("Neeraj", 100),
            new Person("Alex", 10),
            new Person("York", 84),
            new Person("Bob", 55)
    );

    static List<List<Person>> listOfListofPeople = List.of(
            List.of(new Person("John", 20), new Person("Mary", 25)),
            List.of(new Person("Peter", 30), new Person("Neeraj", 100)),
            List.of(new Person("Alex", 10), new Person("York", 84)),
            List.of(new Person("Bob", 55))
    );

    static Map<String, List<Person>> peopleMap2 = Map.of(
            "Group1", List.of(new Person("John", 20), new Person("Mary", 25)),
            "Group2", List.of(new Person("Peter", 30), new Person("Neeraj", 100)),
            "Group3", List.of(new Person("Alex", 10), new Person("York", 84)),
            "Group4", List.of(new Person("Bob", 55))
    );


    /** --------------------------------------------- Main Method --------------------------------------------- */
    public static void main(String[] args) {
        listOfListofPeople.stream()
                .flatMap(Collection::stream)
                .forEach(System.out::println);

    }
}


