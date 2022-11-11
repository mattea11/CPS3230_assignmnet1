package assignment1;

public class itemInfo {

    // protected List<itemInfo> itemList;
    protected String title;
    protected String description;
    protected String url;
    protected String image;
    protected String price;

    // public itemInfo(){
    //     itemList = new ArrayList<>(5);
    // }

    public itemInfo(String title, String decsription, String url, String image, String price){
        this.title = title;
        this.description = decsription;
        this.url = url;
        this.image = image;
        this.price = price;
    }

    // public boolean addItemInfo(itemInfo item){
    //     if(title != null && description != null && url != null && image != null && date != null && price != null){
    //         itemList.add(item);
    //         return true;
    //     }
    //     return false;
    // }

}
