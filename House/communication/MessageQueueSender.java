package communication;

import bean.Message;

import java.util.LinkedList;
import java.util.NoSuchElementException;

// Message sender queue. Implements Singleton pattern
public class MessageQueueSender {

    private LinkedList<Message> messages = new LinkedList<>();
    private static MessageQueueSender instance = null;

    private MessageQueueSender() {}

    public synchronized static MessageQueueSender getInstance()
    {
        if (instance == null)
            instance = new MessageQueueSender();

        return instance;
    }

    public synchronized void addMessage(Message m)
    {
        messages.addLast(m);
        notify();
    }

    public synchronized Message getMessage()
    {
        Message m = null;
        try{
            m = messages.removeFirst();
        }
        catch (NoSuchElementException err) {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return m;
    }
}
