/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.frames;

import chatapplication.database_connection.DatabaseManager;
import chatapplication.main.Frame;
import chatapplication.rooms.RoomManager;
import com.mysql.jdbc.PreparedStatement;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;

/**
 *
 * @author root
 */
public class RoomsFrame extends javax.swing.JInternalFrame {

    private RoomManager roomManager;
    private JDesktopPane desktop;
    private Frame manager;
    private DatabaseManager database;
    private RoomFrame roomFrame;

    /**
     * Creates new form RoomsFrame
     */
    public RoomsFrame(JDesktopPane desktop, Frame manager) {
        this.manager = manager;
        database = manager.Database();
        this.desktop = desktop;
        initComponents();
        roomManager = new RoomManager();
        createRooms();
    }

    public void createRooms() {
        rooms.removeAll();
        rooms.setModel(createRoomList());
    }

    public DefaultListModel createRoomList() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 1; i < 6; i++) {
            model.addElement("room " + i);
        }
        for (int i = 1; i < 6; i++) {
            roomManager.addRoom("room " + i);
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
        rooms = new javax.swing.JList<>();

        setClosable(true);

        rooms.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        rooms.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                roomsMouseMoved(evt);
            }
        });
        rooms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(rooms);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roomsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomsMouseClicked
        String room = rooms.getSelectedValue();
        if (room != null) {
            try {
                PreparedStatement ids = database.Select(null, "room_users");
                ResultSet id = ids.executeQuery();
                int actualId = 0;
                while (id.next()) {
                    actualId = Integer.parseInt(id.getString("id"));
                }
                actualId++;
                System.out.println("actual ID" + (actualId));
                PreparedStatement st = (PreparedStatement) database.connection.prepareStatement("INSERT INTO room_users (id, user, room) VALUES (" + actualId + ", '" + database.user.getUsername() + "', '" + room + "')");
                System.out.println(st.executeUpdate());
            } catch (Exception ex) {
                Logger.getLogger(RoomsFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (roomFrame != null) {
                if (!roomFrame.isVisible()) {
                    roomFrame = new RoomFrame(roomManager.getRoomByName(room), manager.Database(), manager.getClient());
                    try {
                        room = rooms.getSelectedValue();
                        System.out.println(room);
                        manager.getClient().userAdded("roomadd", manager.Database().user.getUsername(), room);
                    } catch (Exception ex) {
                        Logger.getLogger(RoomsFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    roomFrame.setVisible(true);
                    desktop.add(roomFrame);
                    setVisible(false);
                }
            } else {
                roomFrame = new RoomFrame(roomManager.getRoomByName(room), manager.Database(), manager.getClient());
                try {
                    room = rooms.getSelectedValue();
                    System.out.println(room);
                    manager.getClient().userAdded("roomadd", manager.Database().user.getUsername(), room);
                } catch (Exception ex) {
                    Logger.getLogger(RoomsFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                roomFrame.setVisible(true);
                desktop.add(roomFrame);
                setVisible(false);
            }

        }

    }//GEN-LAST:event_roomsMouseClicked

    private void roomsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomsMouseMoved
    }//GEN-LAST:event_roomsMouseMoved
    public RoomFrame getRoom() {
        return roomFrame;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> rooms;
    // End of variables declaration//GEN-END:variables
}
