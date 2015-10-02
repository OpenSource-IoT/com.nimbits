import com.nimbits.client.model.point.Point;
import com.nimbits.client.model.point.PointModel;
import com.nimbits.client.model.user.User;
import com.nimbits.client.model.user.UserModel;
import com.nimbits.client.model.value.Value;
import com.nimbits.io.Nimbits;

/**
 * Create a point and write the current time in ms to it until stopped
 *
 */
public class V3ApiHeartbeat {
    private static final String server = "http://localhost:8888";
    private static final String adminEmail = "admin@example.com";
    private static final String adminPassword = "password12345";


    public static void main(String... args) throws InterruptedException {

        Nimbits adminClient = new Nimbits.NimbitsBuilder()
                .email(adminEmail).token(adminPassword).instance(server).create();

        try {

            User admin = new UserModel.Builder().email(adminEmail).password(adminPassword).create();
            admin = adminClient.addUser(admin);

            Log("Created Admin: " + admin.toString());
        } catch (Throwable throwable) {
            //this will throw an exception if their already is an admin on this box
            Log(throwable.getMessage());
        }

        User me = adminClient.getMe();

        System.out.println(me.toString());

        Point point = new PointModel.Builder().name("timestamp").create();
        point = adminClient.addPoint(me, point);
        while (true) {

            adminClient.recordValue(point, new Value.Builder().doubleValue(System.currentTimeMillis()).create());
            Thread.sleep(1000);
        }

    }

    private static void Log(String s) {
        System.out.println(s);
    }
}
