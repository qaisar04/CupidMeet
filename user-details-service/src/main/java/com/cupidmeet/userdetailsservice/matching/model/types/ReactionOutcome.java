package com.cupidmeet.userdetailsservice.matching.model.types;

/**
 * Результат реакции на пользователя.
 */
public enum ReactionOutcome {

    /**
     * Взаимный лайк (совпадение).
     */
    MUTUAL_LIKE,

    /**
     * Ожидание ответной реакции.
     */
    WAIT_FOR_THE_RETURN_RESPONSE,

    /**
     * Отсутствие результата (противоположные оценки).
     */
    NONE
}