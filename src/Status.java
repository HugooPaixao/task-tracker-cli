public enum Status {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String description;
    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
