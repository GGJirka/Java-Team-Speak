/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.frames;

import chatapplication.connection.Client;
import chatapplication.database_connection.DatabaseManager;
import chatapplication.rooms.Room;
import com.mysql.jdbc.PreparedStatement;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author root
 */
public class RoomFrame extends javax.swing.JInternalFrame {
    private Room room;
    private DatabaseManager database;
    private Client client;
    private DefaultListModel model;

    /**
     * Creates new form RoomFrame
     */
    public RoomFrame(Room room, DatabaseManager database, Client client) {
        this.database = database;
        this.room = room;
        this.client = client;
        initComponents();
        setTitle(room.getRoom());
        createUserList();
        
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent evt) {
                try {
                    onExit();
                } catch (SQLException ex) {
                    Logger.getLogger(RoomFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        userChat.setLineWrap(true);
        userChat.setWrapStyleWord(true);
        chat.setLineWrap(true);
        chat.setWrapStyleWord(true);
    }

    public void createUserList() {
        users.removeAll();
        users.setModel(createUsers());
    }
    public void actions(){
        userChat.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    e.consume();
                    sendButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });              
    }    
    public void userConnected(String username,String roomName){
        if(roomName.equals(room.getRoom())){
            model.addElement(username);
        }
    }
    public void userDisconnected(String username, String roomName){
        if(roomName.equals(room.getRoom())){
            model.removeElement(username);
        }
    }
    public DefaultListModel createUsers() {
        model = new DefaultListModel();
        try {
            PreparedStatement select = database.Select(null, "room_users");
            ResultSet result = select.executeQuery();
            while (result.next()) {
                if (room.getRoom().equalsIgnoreCase(result.getString("room"))) {
                    if(!result.getString("user").equalsIgnoreCase(database.user.getUsername())){
                        model.addElement(result.getString("user"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        users = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userChat = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();
        changeroom_button = new javax.swing.JButton();

        setClosable(true);

        users.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(users);

        jLabel1.setText("Users in room");

        userChat.setColumns(20);
        userChat.setRows(5);
        jScrollPane2.setViewportView(userChat);

        sendButton.setText("send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        chat.setEditable(false);
        chat.setColumns(20);
        chat.setRows(5);
        jScrollPane3.setViewportView(chat);

        changeroom_button.setText("change room");
        changeroom_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeroom_buttonMouseClicked(evt);
            }
        });
        changeroom_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeroom_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(changeroom_button, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendButton)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(14, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeroom_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sendButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        ArrayList<String> users = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            Object obj = model.get(i);
            String user = (String) obj;
            users.add(user);        
        }
        
        client.writeMessage("room",database.user.getUsername(),userChat.getText(),users);
        userChat.setText("");
    }//GEN-LAST:event_sendButtonActionPerformed

    private void changeroom_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeroom_buttonActionPerformed

    }//GEN-LAST:event_changeroom_buttonActionPerformed

    private void changeroom_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeroom_buttonMouseClicked
        this.setVisible(false);
        try {
            PreparedStatement delete = database.Delete("room_users", "user = '" + database.user.getUsername() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(RoomFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_changeroom_buttonMouseClicked
    public void receiveMessage(String fromUser, String toUser, String message) {
        if (toUser.equalsIgnoreCase(database.user.getUsername())) {
            chat.append(fromUser + ": " + message + "\n");
        }
    }

    private void onExit() throws SQLException {
        PreparedStatement delete = database.Delete("room_users", "user = '" + database.user.getUsername() + "'");
        client.chatUserLeft("roomremove",database.user.getUsername(),room.getRoom());
        System.out.println(delete.executeUpdate());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeroom_button;
    private javax.swing.JTextArea chat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea userChat;
    private javax.swing.JList<String> users;
    // End of variables declaration//GEN-END:variables
}
