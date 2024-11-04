package com.cupidmeet.event.listener.extension.model.type;

/**
 * The Status enum represents the possible statuses of an event in the system.
 * It can either be SUCCESS if the event was processed successfully, or FAIL if the event processing failed.
 */
public enum Status {
    /**
     * Represents a successful event processing.
     */
    SUCCESS,
    /**
     * Represents a failed event processing.
     */
    FAIL
}