package Domain;

import Ui.Read;

/**
 *
 * @author Mauricio Herrera
 */
public class GetReadForm {

    public static Read read;

    public static Read getForm() {
        if (read == null) {
            read = new Read();
        }
        return read;
    }

    public static void destroyForm() {
        read = null;
    }
}
