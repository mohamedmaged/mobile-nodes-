package package1;

public class Message {

    public enum msgType {
        msg, ack
    };

    private final String msgBody;
    private Node source;
    private Node destination;
    private msgType type;

    public Message(String msgBody, Node source, Node destination, msgType type) {
        this.msgBody = msgBody;
        this.source = source;
        this.destination = destination;
        this.type = type;
    }

    public String toString() {
        return msgBody;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public msgType getType() {
        return this.type;
    }
}
