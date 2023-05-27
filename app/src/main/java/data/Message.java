package data;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Message {
    String username;
    Date date;
    Long orderingDate;
    String message;

    public Message(String username, Date date, Long orderingDate, String message) {
        this.username = username;
        this.date = date;
        this.orderingDate = orderingDate;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getOrderingDate() {
        return orderingDate;
    }

    public void setOrderingDate(Long orderingDate) {
        this.orderingDate = orderingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", orderingDate=" + orderingDate +
                ", message='" + message + '\'' +
                '}';
    }
}
