package Domain;

import Ui.Enroll;

/**
 *
 * @author Mauricio Herrera
 */
public class GetEnrollForm {

    public static Enroll enroll;

    public static Enroll getForm() {
        if (enroll == null) {
            enroll = new Enroll();
        }
        return enroll;
    }

    public static void destroyForm() {
        enroll = null;
    }
}
