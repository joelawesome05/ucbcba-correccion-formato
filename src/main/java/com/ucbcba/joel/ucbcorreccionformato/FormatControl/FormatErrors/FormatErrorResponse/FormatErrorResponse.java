package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatErrorResponse;

public class FormatErrorResponse {
    private Content content;
    private Position position;
    private Comment comment;
    private String id;
    private boolean error;

    public FormatErrorResponse(Content content, Position position, Comment comment, String id, boolean error) {
        this.content = content;
        this.position = position;
        this.comment = comment;
        this.id = id;
        this.error = error;
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
}
