package teleporting.teleport.Data;

/**
 * Created by Roshini on 29-10-2016.
 */

public class OnDemandData {

    public OnDemandData(String serviceName, String category, int thumbnail) {
        this.category = category;
        this.serviceName = serviceName;
        this.thumbnail = thumbnail;
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    private String serviceName;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    private int thumbnail;

}
