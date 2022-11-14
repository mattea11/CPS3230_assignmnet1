package assignment1;

public class itemInfo {
    protected String title;
    protected String description;
    protected String url;
    protected String image;
    protected String price;

    public itemInfo(String title, String decsription, String url, String image, String price){
        this.title = title;
        this.description = decsription;
        this.url = url;
        this.image = image;
        this.price = price;
    }
}
