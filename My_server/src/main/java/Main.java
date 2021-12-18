import utils.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int PORT_NUMBER = 5556;
    private static ServerSocket serverSocket;
    private static Server clientHandler;
    private static Thread thread;
    private static List<Socket> currentSockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);
        while (true) {
            for (Socket socket :
                    currentSockets) {
                if(socket.isClosed()){
                    currentSockets.remove(socket);
                    continue;
                }
                String socketInfo = "Соединение с клиентом " + socket.getInetAddress() + ":" + socket.getPort() + " установлено.";
                System.out.println(socketInfo);
            }
            Socket socket = serverSocket.accept();
            currentSockets.add(socket);
            clientHandler = new Server(socket);
            thread = new Thread(clientHandler);
            thread.start();
            System.out.flush();
        }
    }

    protected void finalize() throws IOException {
        serverSocket.close();
    }
}
