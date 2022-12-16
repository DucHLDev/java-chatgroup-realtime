package form;

import control.Client;
import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public final class ClientView extends javax.swing.JFrame {

    /**
     * Creates new form ClientView
     */
    Client client;
    private final Connection con;
    private final ConnectView conviewold;
    private int lastX;
    private int lastY;

    public ClientView(Connection con, Client client, ConnectView conviewold) {
        this.con = con;
        this.conviewold = conviewold;
        this.client = client;
        initComponents();
        setLocationRelativeTo(null);
        getDataMess();
    }

    public String getUserInput() {
        return jTextField_UserInput.getText();
    }

    public void set_UserInput(String text) {
        jTextField_UserInput.setText(text);
    }

    public void addChatLeft(String text) {
        chatPanel.addChatLeft(text);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                revalidate();
                repaint();
            }
        });
    }

    public void addChatRight(String text) {
        chatPanel.addChatRight(text);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                revalidate();
                repaint();
            }
        });
    }

    public void getDataMess() {
        // Get Data SQL 
        try {
            if (con == null || con.isClosed()) {
                JOptionPane.showMessageDialog(this, "Connection closed!", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from Mess");
            String ss = "";
            while (rs.next()) {
                if (rs.getString("UserName").equals("SERVER")) {
                    ss = rs.getString("UserName") + ": " + rs.getString("Body") + " ";
                } else {
                    String s_bodychat = rs.getString("Body");
                    String s_username = "[" + rs.getString("UserName") + "]: ";

                    ss = s_username + chiadoan(s_bodychat, s_username.length()); // Chia doan ( them \n vao tin nhan)
                }
                chatPanel.addChatLeft(ss);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        revalidate();
                        repaint();
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        while (true) {
            try {
                if (msgFromGroupChat.length() <= 60) {
                    ketqua = msgFromGroupChat;
                    break;
                }
                if (msgFromGroupChat.indexOf(" ") == -1 || (msgFromGroupChat.indexOf(" ", position_Start + oneLine_maxnumber) - position_Start) > 60) { // Neu khong co khoang trang 
                    flag = false;
                    if (isFirst_run) {
                        position_End = 60 - UserName_Length;
                        oneLine = msgFromGroupChat.substring(position_Start, position_End);
                        position_Start = position_End;
                        isFirst_run = false;
                    } else {
                        oneLine = msgFromGroupChat.substring(position_Start, position_Start + 60);
                        position_Start = position_Start + 60;
                    } // Lay ra 1 dong ( tu vi tri dong truoc den 60 ki tu
                    ketqua += oneLine.trim() + "\n";
                } else {
                    oneLine = msgFromGroupChat.substring(position_Start, msgFromGroupChat.indexOf(" ", position_Start + oneLine_maxnumber)); // Lay ra 1 dong ( tu vi tri dong truoc den 60 ki tu
                    position_Start = msgFromGroupChat.indexOf(" ", position_Start + oneLine_maxnumber); // Luu lai vi tri bat dau cua tung dong
                    ketqua += oneLine.trim() + "\n";
                }
            } catch (StringIndexOutOfBoundsException e) {
                //System.out.println("out index");
                break;
            }
        }
        return ketqua;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel_chat = new javax.swing.JPanel();
        chatPanel = new form.ChatPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_UserInput = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Group Chat Program");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(237, 242, 245));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/group.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel3)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 370, 670));
        jPanel7.setBackground(new Color(255,255,255,125));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/14773.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1428, 820));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 370, 820));

        jPanel2.setBackground(new java.awt.Color(46, 46, 46));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(46, 46, 46));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-information-36.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel8)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 60, 60));

        jPanel6.setBackground(new java.awt.Color(46, 46, 46));
        jPanel6.setAutoscrolls(true);

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home (1).png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel9)
                .addGap(13, 13, 13))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 60, 60));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 820));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel_chat.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel_chatLayout = new javax.swing.GroupLayout(jPanel_chat);
        jPanel_chat.setLayout(jPanel_chatLayout);
        jPanel_chatLayout.setHorizontalGroup(
            jPanel_chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_chatLayout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(chatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel_chatLayout.setVerticalGroup(
            jPanel_chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_chatLayout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(chatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.add(jPanel_chat, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 90, 980, 670));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_circled_x_24px.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 10, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-30.png"))); // NOI18N
        jLabel1.setAlignmentX(1.0F);
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 20, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_heart_50px.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 50, 40));

        jTextField_UserInput.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField_UserInput.setForeground(new java.awt.Color(153, 153, 153));
        jTextField_UserInput.setText("Type your message here");
        jTextField_UserInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));
        jTextField_UserInput.setMargin(new java.awt.Insets(2, 10, 2, 2));
        jTextField_UserInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_UserInputMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField_UserInputMousePressed(evt);
            }
        });
        jTextField_UserInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_UserInputActionPerformed(evt);
            }
        });
        jTextField_UserInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_UserInputKeyPressed(evt);
            }
        });
        jPanel5.add(jTextField_UserInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 890, 40));
        jPanel5.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 980, 12));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 760, 980, 60));

        jLabel2.setBackground(new java.awt.Color(102, 255, 102));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/chat-applicaiton_1.png"))); // NOI18N
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel2MouseDragged(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 980, 90));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 1360, 820));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        // Move frame by the mouse delta
        setLocation(getLocationOnScreen().x + x - lastX,
                getLocationOnScreen().y + y - lastY);
        lastX = x;
        lastY = y;
    }//GEN-LAST:event_jLabel2MouseDragged

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        lastX = evt.getXOnScreen();
        lastY = evt.getYOnScreen();
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        try {
            if (!jTextField_UserInput.equals("") || jTextField_UserInput.equals("Type your message here")) {
                client.sendMessage(this, con);
                jTextField_UserInput.setText("");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        try {
            client.sendHeart(this, con);
        } catch (IOException ex) {
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jTextField_UserInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_UserInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_UserInputActionPerformed

    private void jTextField_UserInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_UserInputKeyPressed
        if (jTextField_UserInput.getText().equals("Type your message here")) {
            jTextField_UserInput.setText("");
        }
        jTextField_UserInput.setForeground(new java.awt.Color(0, 0, 0));

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && !jTextField_UserInput.getText().equals("")) {
            try {
                client.sendMessage(this, con);
                jTextField_UserInput.setText("");
            } catch (IOException ex) {
                Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTextField_UserInputKeyPressed

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        jPanel6.setBackground(new java.awt.Color(46, 46, 46));
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        jPanel6.setBackground(new java.awt.Color(243, 243, 243));
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        try {
            conviewold.setVisible(true);
            client.closeSocketClientHandler();
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        jPanel4.setBackground(new java.awt.Color(46, 46, 46));
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        jPanel4.setBackground(new java.awt.Color(243, 243, 243));
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        JOptionPane.showMessageDialog(this, "Phần mềm trò chuyện nhóm\nBy MyTeam", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jTextField_UserInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_UserInputMouseClicked
        if (jTextField_UserInput.getText().equals("Type your message here")) {
            jTextField_UserInput.setText("");
        }
        jTextField_UserInput.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_jTextField_UserInputMouseClicked

    private void jTextField_UserInputMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_UserInputMousePressed
        if (jTextField_UserInput.getText().equals("Type your message here")) {
            jTextField_UserInput.setText("");
        }
        jTextField_UserInput.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_jTextField_UserInputMousePressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private form.ChatPanel chatPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel_chat;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField_UserInput;
    // End of variables declaration//GEN-END:variables
}
