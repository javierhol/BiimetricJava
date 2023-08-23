package Domain;

/**
 *
 * @author Mauricio Herrera
 */
public class ConfigDTO {

    private String id;
    private String serial_number_pc;
    private String url_api;   
    private String browser;
    private String status;

    public ConfigDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial_number_pc() {
        return serial_number_pc;
    }

    public void setSerial_number_pc(String serial_number_pc) {
        this.serial_number_pc = serial_number_pc;
    }

    
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }
    
    
    

}
