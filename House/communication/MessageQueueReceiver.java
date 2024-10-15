package communication;

import bean.Message;

import java.util.LinkedList;

// Message receiver queue. Implements Singleton pattern
public class MessageQueueReceiver {

    private LinkedList<Message> messages = new LinkedList<>();
    private static MessageQueueReceiver instance = null;

    private MessageQueueReceiver() {}

    public synchronized static MessageQueueReceiver getInstance()
    {
        if (instance == null)
            instance = new MessageQueueReceiver();

        return instance;
    }

    public synchronized void addMessage(Message m)
    {
        messages.addLast(m);
        notify();
    }

    public synchronized Message getMessage()
    {
        if(messages.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return messages.removeFirst();

    }
}
