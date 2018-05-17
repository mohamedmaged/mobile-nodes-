package package1;

import java.awt.Point;

public class Node extends Point {

    private Integer id = 0;
    private final String name;
    private Message message;

    private static int counter = 0;

    public Node(String name) {
        super();
        this.name = name;
        this.id = ++counter;
    }

    public Node(String name, int x, int y) {
        super(x, y);
        this.name = name;
        this.id = ++counter;
    }

    public Node(String name, Node n) {
        super(n);
        this.name = name;
        this.id = ++counter;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    //send
    public Message getMessage() {
        return this.message;
    }

    //receive
    public void setMessage(Message message) {
        this.message = message;
        if (message.getDestination() == this) {
            if (message.getType() == Message.msgType.msg) {
                //acknowledge source
                System.out.println(this.toString() + " : Message from:" + message.getSource().toString() + " content:" + message.toString());
                sendAck();
            } else if (message.getType() == Message.msgType.ack) {
                System.out.println(this.toString() + " : Message from:" + message.getSource().toString() + " content:" + message.toString());
            }
        }
    }

    private void sendAck() {
        Message ack = new Message("Message Received", this, message.getSource(), Message.msgType.ack);
        this.message = ack;
        int cost = Network.getAlgorithm().startTransmission(ack);
    }

    public int createMessage(String msgBody, Node destination) {
        Message message = new Message(msgBody, this, destination, Message.msgType.msg);
        this.message = message;
        int cost = Network.getAlgorithm().startTransmission(message);
        return cost;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Node other = (Node) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
