package in.semicolonindia.parentcrm.beans;

/**
 * Created by Rupesh on 07-08-2017.
 */
@SuppressWarnings("ALL")
public class BookListNames {

    private String name;
    private String author;
    private String price;
    private String sClassName;
    private String description;
    private String bookID;
    private String studID;

    public BookListNames(String name, String author, String price, String sClassName,
                         String description, String bookID, String studID) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.sClassName = sClassName;
        this.description = description;
        this.bookID = bookID;
        this.studID = studID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public String getClassName() {
        return sClassName;
    }

    public String getDescription() {
        return description;
    }

    public String getBookID() {
        return bookID;
    }

    public String getStudID() {
        return studID;
    }
}