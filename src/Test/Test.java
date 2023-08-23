package Test;

import Domain.ConfigDTO;
import Model.DmlJsonDao;
import Model.IDmlJsonDao;
import java.io.IOException;

/**
 *
 * @author Mauricio Herrera
 */
public class Test {

    public static void main(String[] args) throws IOException {
        IDmlJsonDao config = new DmlJsonDao();
//        System.out.println(config.createFileJson());
        ConfigDTO cf = new ConfigDTO();
        cf.setId("123456");
        cf.setSerial_number_pc("1234657987");
        cf.setUrl_api("http:13213546546");
        cf.setBrowser("google");
        cf.setStatus("Inactive");
        System.out.println(config.insert(cf));
    }
}
