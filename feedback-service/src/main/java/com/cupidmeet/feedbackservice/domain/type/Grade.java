package com.cupidmeet.feedbackservice.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление, представляющее оценки от 1 до 5.
 */
@Getter
@AllArgsConstructor
public enum Grade {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    /**
     * Значение оценки.
     */
    private final int value;
}