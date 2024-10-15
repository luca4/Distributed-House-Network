package core;

import java.util.Calendar;

// Utility class
public class Utils {

    private static boolean canTerminate = true;
    private static final Object lockCanTerminate = new Object();

    // Blocks program exit. Useful for critical operations
    public static void terminate() {
        synchronized (lockCanTerminate) {
            if (!canTerminate)
                try {
                    lockCanTerminate.wait();
                }catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void setCanTerminate(boolean canTerm) {
        synchronized (lockCanTerminate) {
            canTerminate = canTerm;

            if(canTerminate) {
                lockCanTerminate.notify();
            }
        }
    }

    // Return elapsed seconds from midnight
    public static long getTimestamp()
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return System.currentTimeMillis()-c.getTimeInMillis();
    }
}
