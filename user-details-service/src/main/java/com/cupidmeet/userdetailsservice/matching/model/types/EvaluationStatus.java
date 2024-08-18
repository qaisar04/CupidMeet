package com.cupidmeet.userdetailsservice.matching.model.types;

/**
 * Перечисление, представляющее статус оценки пользователя.
 */
public enum EvaluationStatus {
    /**
     * Оценка активна и учитывается при подборе пользователей.
     */
    ACTIVE,
    /**
     * Оценка устарела и больше не учитывается при подборе пользователей.
     */
    OBSOLETE
}