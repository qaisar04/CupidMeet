package com.cupidmeet.feedbackservice.service;

import com.cupidmeet.feedbackservice.domain.dto.ComplaintCreateRequest;
import com.cupidmeet.feedbackservice.domain.dto.ComplaintResponse;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления жалобами пользователей.
 */
public interface ComplaintService {

    /**
     * Создает новую жалобу.
     *
     * @param request данные для создания жалобы
     */
    void create(ComplaintCreateRequest request);

    /**
     * Возвращает список всех жалоб.
     *
     * @return список жалоб
     */
    List<ComplaintResponse> get();

    /**
     * Возвращает жалобу по указанному идентификатору.
     *
     * @param id идентификатор жалобы
     * @return информация о жалобе
     */
    ComplaintResponse get(UUID id);
}