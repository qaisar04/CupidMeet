package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.dto.ComplaintFeedbackDto;
import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;

public interface FeedBackService {
    /**
     * this method response for creating user's complaint from dto
     * and also sets status "NEW" by default
     * @param entity from controller
     * @return the original dto
     */
    ComplaintFeedbackDto createComplaint(Complaint entity);
    /**
     *this method response for creating user's review from dto
     * @param entity from controller
     * @return the original dto
     */
    ComplaintFeedbackDto createFeedback(Feedback entity);
}
