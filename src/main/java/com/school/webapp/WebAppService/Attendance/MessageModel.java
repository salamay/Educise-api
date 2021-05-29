package com.school.webapp.WebAppService.Attendance;

public class MessageModel {
    private String api_token;
    private String to;
    private String from;
    private String body;
    private String gateway;
    private String append_sender;

    public MessageModel() {
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getAppend_sender() {
        return append_sender;
    }

    public void setAppend_sender(String append_sender) {
        this.append_sender = append_sender;
    }
}
