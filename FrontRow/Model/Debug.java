package FrontRow.Model;

/**
 * Created by chill on 4/25/16.
 */
public class Debug {
    private static final boolean DEBUG = true;
//	public static boolean DEBUG = false;

    public static void set(boolean s){
//		DEBUG = s;
        Debug.print("Debugging is on.\n");
    }


    public static void print(Object msg){
        if (DEBUG){
            System.out.println(msg.toString());
        }
    }
}