package kz.baltabayev.userdetailsservice.model.types;

import lombok.Getter;

@Getter
public enum PersonalityType {
    ARCHITECT("Архитектор", "INTJ-A / INTJ-T"),
    LOGICIAN("Ученый", "INTP-A / INTP-T"),
    COMMANDER("Командир", "ENTJ-A / ENTJ-T"),
    DEBATER("Полемист", "ENTP-A / ENTP-T"),
    ADVOCATE("Активист", "INFJ-A / INFJ-T"),
    MEDIATOR("Посредник", "INFP-A / INFP-T"),
    PROTAGONIST("Тренер", "ENFJ-A / ENFJ-T"),
    CAMPAIGNER("Борец", "ENFP-A / ENFP-T"),
    LOGISTICIAN("Администратор", "ISTJ-A / ISTJ-T"),
    DEFENDER("Защитник", "ISFJ-A / ISFJ-T"),
    EXECUTIVE("Менеджер", "ESTJ-A / ESTJ-T"),
    CONSUL("Консул", "ESFJ-A / ESFJ-T"),
    VIRTUOSO("Виртуоз", "ISTP-A / ISTP-T"),
    ADVENTURER("Артист", "ISFP-A / ISFP-T"),
    ENTREPRENEUR("Делец", "ESTP-A / ESTP-T"),
    ENTERTAINER("Развлекатель", "ESFP-A / ESFP-T");

    private final String description;
    private final String acronym;

    PersonalityType(String description, String acronym) {
        this.description = description;
        this.acronym = acronym;
    }
}

