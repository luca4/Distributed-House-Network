package communication;

// Instantiate a fixed number of threads
public class ReceiverThreadPool {

    private final int MAX_THREADS = 5;

    public ReceiverThreadPool()
    {
        ReceiverThread rt;

        for (int i=0; i<MAX_THREADS; i++)
        {
            rt = new ReceiverThread();
            rt.start();
        }
    }
}
