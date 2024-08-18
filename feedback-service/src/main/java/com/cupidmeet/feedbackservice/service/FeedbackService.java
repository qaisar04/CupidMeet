package com.cupidmeet.feedbackservice.service;

import com.cupidmeet.feedbackservice.domain.dto.FeedbackCreateRequest;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackResponse;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackUpdateRequest;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления отзывами пользователей.
 */
public interface FeedbackService {

    /**
     * Создает новый отзыв на основе предоставленных данных.
     *
     * @param request данные для создания отзыва
     */
    void create(FeedbackCreateRequest request);

    /**
     * Возвращает список всех отзывов.
     *
     * @return список отзывов
     */
    List<FeedbackResponse> get();

    /**
     * Обновляет существующий отзыв.
     *
     * @param request данные для обновления отзыва
     * @param id идентификатор отзыва, которую нужно обновить
     */
    FeedbackResponse update(UUID id, FeedbackUpdateRequest request);
}