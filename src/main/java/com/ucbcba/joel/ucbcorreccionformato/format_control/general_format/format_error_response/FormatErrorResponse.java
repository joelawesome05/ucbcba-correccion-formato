package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response;

public class FormatErrorResponse {
    private Content content;
    private Position position;
    private Comment comment;
    private String id;
    private boolean error;
    private String type;

    public FormatErrorResponse(Content content, Position position, Comment comment, String id, boolean error,String type) {
        this.content = content;
        this.position = position;
        this.comment = comment;
        this.id = id;
        this.error = error;
        this.type = type;
    }


    public String getId() {
        return id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
