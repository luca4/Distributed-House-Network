package communication;

// Instantiate a fixed number of threads
public class SenderThreadPool {

    private final int MAX_THREADS = 5;

    public SenderThreadPool()
    {
        SenderThread st;

        for (int i=0; i<MAX_THREADS; i++)
        {
            st = new SenderThread();
            st.start();
        }
    }
}
