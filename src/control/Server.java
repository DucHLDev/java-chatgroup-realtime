package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;
    
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void startServer() {
        try {
           while (!serverSocket.isClosed()) { // Kiem tra lang nghe
               Socket socket = serverSocket.accept(); // Chap nhap ket noi tu phia client, serverSocket.accept() tra ve 1 doi tuong socket
               System.out.println("Mot Nguoi Dung Da Ket Noi!");
               ClientHandler clientHandler = new ClientHandler(socket); // Doi tuong xuly nguoidung ( tiếp nhận 1 socket) , Muc dich clientHandler mo rong thread
               Thread thread = new Thread(clientHandler); // Chi dinh 1 luong se tiep nhan doituong xuly nguoidung
               thread.start();
           }
        } catch (IOException e) {
                
        }
    }
    
   public void closeServerSocket() {
       try {
           if(serverSocket != null) serverSocket.close();
       }catch (IOException e) {
           e.printStackTrace();
       }      
   }
   
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234); // Khoi tao ServerSocket lang nghe port (1234)
        Server server = new Server(serverSocket); // Khoi tao sever voi socketserver
        server.startServer();
    }
}
