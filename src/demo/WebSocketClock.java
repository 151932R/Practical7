package demo;

/**
 * Created by KyleV_000 on 28/11/2016.
 */

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/clock")
public class WebSocketClock{
    static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @OnOpen
    public void showTime(Session session){
        System.out.println("Opened : " + session.getId());
        timer.scheduleAtFixedRate(() -> sendTime(session), 0,1, TimeUnit.SECONDS); // Lambda function (Anonymous Function)
    }

    private void sendTime(Session session){
        try{
            String data = "Time: " + LocalTime.now().format(timeFormatter);
            session.getBasicRemote().sendText(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnMessage
    public void onMessage(String txt, Session session) throws IOException{
        System.out.println("Message received " + txt);
        session.getBasicRemote().sendText(txt);
    }
}
