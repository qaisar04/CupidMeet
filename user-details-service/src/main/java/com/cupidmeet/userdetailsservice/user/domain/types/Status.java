package com.cupidmeet.userdetailsservice.user.domain.types;

/**
 * Перечисление, представляющее статусы пользователей в системе.
 */
public enum Status {
    /**
     * Активный пользователь.
     */
    ACTIVE,

    /**
     * Неактивный пользователь.
     */
    INACTIVE,

    /**
     * Заблокированный пользователь.
     */
    BANNED,
}