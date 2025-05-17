package DiseñoModular;

public class Magazine extends Document implements Storable {
    private String publisher;
    private int issueNumber;
    private String monthYear;
    private String theme;

    private Magazine(Builder builder) {
        super(builder.id, builder.title, null, builder.publicationYear);
        this.publisher = builder.publisher;
        this.issueNumber = builder.issueNumber;
        this.monthYear = builder.monthYear;
        this.theme = builder.theme;
    }

    @Override
    public String display() {
        return "[MAGAZINE] \"" + title + "\" - Publisher: " + publisher;
    }

    public static class Builder {
        private int id;
        private String title;
        private int publicationYear;
        private String publisher;
        private int issueNumber;
        private String monthYear;
        private String theme;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPublicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public Builder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setIssueNumber(int issueNumber) {
            this.issueNumber = issueNumber;
            return this;
        }

        public Builder setMonthYear(String monthYear) {
            this.monthYear = monthYear;
            return this;
        }

        public Builder setTheme(String theme) {
            this.theme = theme;
            return this;
        }

        public Magazine build() {
            return new Magazine(this);
        }
    }
}
