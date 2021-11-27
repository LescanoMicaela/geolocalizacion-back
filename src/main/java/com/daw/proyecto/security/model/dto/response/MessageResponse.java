package com.daw.proyecto.security.model.dto.response;

/**
 * The type Message response.
 */
public class MessageResponse {
    private String message;

    /**
     * Instantiates a new Message response.
     *
     * @param message the message
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}