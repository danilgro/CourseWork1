package ITMO.coursework.model.enums;

public enum WelderLevel {

    WELDER_LEVEL_1("Специалист сварочного производства 1 уровня (аттестованный сварщик)"),
    WELDER_LEVEL_2("Специалист сварочного производства 2 уровня (аттестованный мастер-сварщик)"),
    WELDER_LEVEL_3("Специалист сварочного производства 3 уровня (аттестованный технолог-сварщик)"),
    WELDER_LEVEL_4("Специалист сварочного производства 4 уровня (аттестованный инженер-сварщик)");

    String title;

    WelderLevel(String title) {
        this.title = title;
    }
}
