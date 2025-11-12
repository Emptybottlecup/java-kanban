package Timetable.Components;

public class Coach {
    //фамилия
    private final String surname;
    //имя
    private final String name;
    //отчество
    private final String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

}
