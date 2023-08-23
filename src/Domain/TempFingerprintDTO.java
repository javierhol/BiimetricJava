package Domain;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author Mauricio Herrera
 */
public class TempFingerprintDTO implements Serializable {

    private String id;
    private String image;
    private String fingerprint;
    private String user_id;
    private String name;
    private String finger_name;
    private String serial_number_pc;
    private String option;
    private String user_id_number;
    private String text;

    public TempFingerprintDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFinger_name() {
        return finger_name;
    }

    public void setFinger_name(String finger_name) {
        this.finger_name = finger_name;
    }

    public String getSerial_number_pc() {
        return serial_number_pc;
    }

    public void setSerial_number_pc(String serial_number_pc) {
        this.serial_number_pc = serial_number_pc;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getUser_id_number() {
        return user_id_number;
    }

    public void setUser_id_number(String user_id_number) {
        this.user_id_number = user_id_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
