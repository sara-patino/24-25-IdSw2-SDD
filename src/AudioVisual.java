public class Audiovisual extends Document {
    private String format;
    private int duration;
    private String topic;

    public Audiovisual(int id, String title, String author, int publicationYear, String format, int duration,
            String topic) {
        super(id, title, author, publicationYear);
        this.format = format;
        this.duration = duration;
        this.topic = topic;
    }

    @Override
    public String display() {
        return "[AUDIOVISUAL] \"" + title + "\" - Format: " + format;
    }
}
