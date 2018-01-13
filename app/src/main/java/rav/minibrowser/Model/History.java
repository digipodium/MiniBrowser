package rav.minibrowser.Model;

/**
 * Created by Madan's-PC on 7/14/2017.
 */

public class History {
    public String url;
    public long id;


    public History(String url , long id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    public long getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setId(long id) {
        this.id = id;
    }
}
