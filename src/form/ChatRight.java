/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;
import java.awt.Color;
import swing.TextBubbleBorder;
/**
 *
 * @author Admin
 */
public class ChatRight extends javax.swing.JPanel {

    /**
     * Creates new form ChatRight
     * @param text
     */
    public ChatRight(String text) {
        initComponents();
        txt1.setText(text);
        txt1.setBorder(new TextBubbleBorder(Color.WHITE,2,16,16));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt1 = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(255, 255, 255));

        txt1.setEditable(false);
        txt1.setBackground(new java.awt.Color(204, 204, 255));
        txt1.setBorder(null);
        txt1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt1.setMargin(new java.awt.Insets(9, 6, 6, 6));
        add(txt1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane txt1;
    // End of variables declaration//GEN-END:variables
}