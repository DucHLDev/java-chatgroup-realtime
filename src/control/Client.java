package control;

import form.ClientView;
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  
import javax.swing.JOptionPane;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    String logchat = "";  
    
    public Client(Socket socket, String username) { // Khoi tao User voi 1 socket, Usernam
        try {
            
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // Xuat Luong ra cua socket(ClientHandler vs Client)
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Lay Luong ra cua socket(ClientHandler vs Client)
            
            this.username = username;

            bufferedWriter.write(this.username); // Xuat Username -> Socket
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    
    public void sendMessage(ClientView clientGui, Connection con) throws IOException {
        //Get Date_time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String messUserinput = clientGui.getUserInput();

        // xuat socket
        bufferedWriter.write(" [" + username + "]: " + messUserinput); // Xuat Mess -> Socket
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // Insert mess SQL
        try {
            if (con==null || con.isClosed()) {
                JOptionPane.showMessageDialog(clientGui, "Connection closed!", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return;
            }                               
            messUserinput = messUserinput.replaceAll("'", "''"); // sua loi ky tu dac biet 's chen vao SQL
            Statement s = con.createStatement();
            int r=s.executeUpdate("insert into Mess ([Time], [UserName], [Body]) values('"+dtf.format(now)+"',N'"+username+"',N'"+messUserinput+"')");            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendHeart(ClientView clientGui, Connection con) throws IOException {
        //Get Date_time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String messUserinput = "♥";
        
        // xuat socket
        bufferedWriter.write("[" + username + "]: " + messUserinput); // Xuat Mess -> Socket
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // Insert mess SQL
        try {
            if (con==null || con.isClosed()) {
                JOptionPane.showMessageDialog(clientGui, "Connection closed!", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return;
            }                               
        Statement s = con.createStatement();
        int r=s.executeUpdate("insert into Mess ([Time], [UserName], [Body]) values('"+dtf.format(now)+"',N'"+username+"',N'"+messUserinput+"')");             
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    public void listenForMessage(ClientView clientGui) {
        new Thread(new Runnable() { // Khoi tao 1 luong moi de User da nhiem, vua gui tin nhan, vua nhan tin
            @Override
            public void run() {             
                while(socket.isConnected()) { // Socket con mo, thi con lien tuc nghe ngong'
                    String msgFromGroupChat;                   
                    try {   
                        msgFromGroupChat = bufferedReader.readLine(); // Lay Thong Bao + Mess tu lop xuly
                        
                        if(msgFromGroupChat == null) break;
                       
                        if(msgFromGroupChat.charAt(1) == '*') {
                            String username_send = msgFromGroupChat.substring(3, msgFromGroupChat.indexOf(':') + 1);
                            String msgFromGroupChat_noName = msgFromGroupChat.substring(msgFromGroupChat.indexOf(':') + 1, msgFromGroupChat.length());
                            msgFromGroupChat = chiadoan(msgFromGroupChat_noName.trim(), username_send.length() - 2); // Chia doan ( them \n vao tin nhan)
                            clientGui.addChatRight(username_send + " " +  msgFromGroupChat);
                        } 
                        else {
                            String username_send = msgFromGroupChat.substring(0, msgFromGroupChat.indexOf(':') + 1);
                            String msgFromGroupChat_noName = msgFromGroupChat.substring(msgFromGroupChat.indexOf(':') + 1, msgFromGroupChat.length());
                            msgFromGroupChat = chiadoan(msgFromGroupChat_noName.trim(), username_send.length() - 2); // Chia doan ( them \n vao tin nhan)
                            
                            clientGui.addChatLeft(username_send + " " +  msgFromGroupChat);
                        }
                    } catch (Exception e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }   
                }          
            }
        }).start();
    }
    
    public void closeSocketClientHandler() throws IOException {
        bufferedWriter.write("closesocket989"); // Xuat closesocket -> Socket
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    
    public static String removeCharAt(String s, int pos) {
      return s.substring(0, pos) + s.substring(pos + 1);
   }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
            e.printStackTrace();
        }
    }
    
    public String chiadoan(String msgFromGroupChat, int UserName_Length) { 
        String ketqua = "";
        String oneLine;
        int position_Start = 0;
        int position_End = 0;
        int oneLine_maxnumber = 60; // So luong ky tu toi da 1 dong
        boolean isFirst_run = true;
        boolean flag = false;
        msgFromGroupChat = msgFromGroupChat.replaceAll("\\n", ""); // thay the khoang trang thanh "-"
        while(true) {
            try {
                if(msgFromGroupChat.length() <= 60) {
                    ketqua = msgFromGroupChat;
                    break;
                }
                if(msgFromGroupChat.indexOf(" ") == -1 || ( msgFromGroupChat.indexOf(" ", position_Start + oneLine_maxnumber) - position_Start) > 60) { // Neu khong co khoang trang 
                    flag =  false;
                    if(isFirst_run) {
                        position_End = 60 - UserName_Length;
                        oneLine = msgFromGroupChat.substring(position_Start, position_End);
                        position_Start = position_End;
                        isFirst_run = false;
                    } 
                    else {
                        oneLine = msgFromGroupChat.substring(position_Start, position_Start + 60);
                        position_Start = position_Start + 60;
                    } // Lay ra 1 dong ( tu vi tri dong truoc den 60 ki tu
                    ketqua += oneLine.trim() + "\n";
                } 
                else {
                    oneLine = msgFromGroupChat.substring(position_Start, msgFromGroupChat.indexOf(" ", position_Start + oneLine_maxnumber)); // Lay ra 1 dong ( tu vi tri dong truoc den 60 ki tu
                    position_Start = msgFromGroupChat.indexOf(" ", position_Start + oneLine_maxnumber); // Luu lai vi tri bat dau cua tung dong
                    ketqua += oneLine.trim() + "\n";
                }
            } catch(StringIndexOutOfBoundsException e) {
                //System.out.println("out index");
                break;
            }  
        }
        return ketqua;
    }
    
    public Socket getSocket() {
        return socket;
    }
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }    
    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
}
