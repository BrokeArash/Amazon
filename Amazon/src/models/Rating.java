package models;

public class Rating {
    private final Costumer user;
    private final String message;
    private final int rate;

    public Rating(Costumer user, String message, int rate) {
        this.user = user;
        this.message = message;
        this.rate = rate;
    }

    public Costumer getUser() {
        return user;
    }

    public String getName() {
        return this.user.getFirstName() + " " + this.getUser().getLastName();
    }

    public String getMessage() {
        return message;
    }

    public int getRate() {
        return rate;
    }
}


