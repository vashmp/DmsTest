import static java.lang.Math.random;

public class Person extends DmsTest{
    static String firstName;
    static String lastName;
    static String middleName;
    static String telephoneName;

    public static Person generateRandomPerson(String[] args) {
        args = getRandomValues();
        Person.firstName = args[0];
        Person.lastName = args[1];
        Person.middleName = args[2];
        Person.telephoneName = args[3];
        return new Person();
    }

    private static String randomName() {
        String[] namesField = {"Вася", "Петя", "Артем", "Кузя"};
        int RandomValue = 0 + (int) (random() * 4);
        return namesField[RandomValue];

    }

    private static String randomLastName() {
        String[] lastNamesField = {"Пупкин", "Петров", "Иванов", "Козлов"};
        int RandomValue = 0 + (int) (random() * 4);
        return lastNamesField[RandomValue];

    }

    private static String randomMiddleName() {
        String[] middleNamesField = {"Васильевич", "Петрович", "Кузьмич", "Ильич"};
        int RandomValue = 0 + (int) (random() * 4);
        return middleNamesField[RandomValue];

    }

    private static String randomTelephone() {
        String[] telephoneField = {"9999999999", "8888888888", "7777777777", "6666666666"};
        int RandomValue = 0 + (int) (random() * 4);
        return telephoneField[RandomValue];
    }

    static String[] getRandomValues() {
        String[] randomPerson = new String[4];
        randomPerson[0] = randomName();
        randomPerson[1] = randomLastName();
        randomPerson[2] = randomMiddleName();
        randomPerson[3] = randomTelephone();
        return randomPerson;

    }
}
