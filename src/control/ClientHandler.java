package control;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util. ArrayList;


public class ClientHandler implements Runnable {    // ClientHandler extends Thread hoac ke thua tu interface Runnable
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList(); // Luu tru danh sach nguoi dung 
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private Connection con;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // Xuat Luong ra cua socket(ClientHandler vs Client)
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Lay Luong vao cua socket(ClientHandler vs Client)
            this.clientUsername = bufferedReader.readLine(); // Đọc 1 dòng ở socket, do user gửi vào. ( Đọc tên)
            clientHandlers.add(this); // Them 1 user vao mảng

            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
            // Insert notification SQL
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String dbUrl = "jdbc:sqlserver://ADMIN-PC:1433;databaseName=MessLibrary;user=sa;password=sa2008";
                con = DriverManager.getConnection(dbUrl);
                if (con == null || con.isClosed()) {
                    System.out.print("Insert notification error connect SQL");
                    return;
                }
                Statement s = con.createStatement();
                int r = s.executeUpdate("insert into Mess ([UserName], [Body]) values('" + "SERVER" + "','" + clientUsername + " has entered the chat!" + "')");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() { // Phuong thuc doc cac du lieu tu client gui den
        String messageFromClient;
        while(socket.isConnected()) { // Luon luon doc vao tin nhan, va gui tin nhan cho cac Client
            try {
                messageFromClient = bufferedReader.readLine(); // Đọc 1 dòng ở socket, do user gửi vào. (Đọc tin nhắn)      
                if(messageFromClient.equals("closesocket989")) { // Kiem tra re-use
                    socket.close();
                    closeEverything(socket, bufferedReader, bufferedWriter); 
                    break;
                }
                else {
                    broadcastMessage(messageFromClient);
                    broadcastMessageForMe(messageFromClient);
                }              
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter); 
                break;
            }
        }
    }
    public void broadcastMessage(String messageToSend){       
        for (ClientHandler clientHandler : clientHandlers) {
            try {
               if(!clientHandler.clientUsername.equals(clientUsername)){ // Client User nao khong phai nguoi gui thi moi hien thi tin nhan
                   clientHandler.bufferedWriter.write(" " + messageToSend + " "); // Xuat tin nhan vao socket
                   clientHandler.bufferedWriter.newLine(); // Xuat them dau xuong hang vao socket
                   clientHandler.bufferedWriter.flush(); // bufferedWriter xuat vao bo nho dem, flush de day no di
               }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
     public void broadcastMessageForMe(String messageToSend){       
        for (ClientHandler clientHandler : clientHandlers) {
            try {
               if(clientHandler.clientUsername.equals(clientUsername)){ // Client User nao khong phai nguoi gui thi moi hien thi tin nhan
                   clientHandler.bufferedWriter.write(" * " + messageToSend + " "); // Xuat tin nhan vao socket
                   clientHandler.bufferedWriter.newLine(); // Xuat them dau xuong hang vao socket
                   clientHandler.bufferedWriter.flush(); // bufferedWriter xuat vao bo nho dem, flush de day no di
               }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    
    public void removeClientHandler() {
        //Get Date_time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
        try {
            if (con==null || con.isClosed()) {
                System.out.print("Insert notification error connect SQL");
                return;
            }                               
            Statement s = con.createStatement();
            int r = s.executeUpdate("insert into Mess ([UserName], [Body]) values('" + "SERVER" + "','" + clientUsername + " has left the chat!" + "')");
        } catch (Exception e) {
        }
        clientHandlers.remove(this);
    }
    
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferediriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
        }
    }
}

