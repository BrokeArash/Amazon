package models;

public record Result(boolean isTrue, String message) {

    @Override
    public String toString() {
        return message;
    }
}
