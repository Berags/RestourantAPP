package edu.unifi.controller;
import edu.unifi.Notifier;
import edu.unifi.model.entities.Room;
import edu.unifi.model.entities.Table;
import edu.unifi.model.orm.dao.CheckDAO;
import edu.unifi.model.orm.dao.RoomDAO;
import edu.unifi.model.orm.dao.TableDAO;
import edu.unifi.view.RoomUpdateTool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

public class RoomEditDeletionToolController {
    private static List<Room> rooms;

    public List<Room> getFilteredRooms(String filter) {
        rooms = RoomDAO.getInstance().getAll();
        return rooms.stream().filter(room -> room.getName().toLowerCase().contains(filter.toLowerCase())).toList();
    }

    public static class RoomEditController extends Observable implements ActionListener {
        private Room room;
        private final RoomUpdateTool roomUpdateTool;

        public RoomEditController(Room room, RoomUpdateTool roomUpdateTool) {
            this.room = room;
            this.roomUpdateTool = roomUpdateTool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String roomName = roomUpdateTool.getNameTextField().getText();
            if (roomName.isEmpty() || !java.util.Objects.isNull(RoomDAO.getInstance().getByName(roomName))) {
                setChanged();
                notifyObservers(Notifier.Message.build(MessageType.ERROR, "Invalid room name"));
                return;
            }
            room = RoomDAO.getInstance().getById(room.getId());
            room.setName(roomName);
            RoomDAO.getInstance().update(room);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.UPDATE_ROOM, room.getName() + " updated successfully"));
            roomUpdateTool.dispose();
        }
    }

    public static class RoomDeletionController extends Observable implements ActionListener {
        private Room room;

        public RoomDeletionController(Room room) {
            this.room = room;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            for (Table t : room.getTables()) {
                if (!java.util.Objects.isNull(CheckDAO.getInstance().getValideCheckByTable(t))) {
                    setChanged();
                    notifyObservers(Notifier.Message.build(MessageType.ERROR, "Cannot close the room because some tables have an opened check"));
                    return;
                }
            }

            for (Table t : room.getTables()) {
                TableDAO.getInstance().delete(t);
            }

            rooms.remove(room);
            if (rooms.isEmpty())
                rooms = null;
            room = RoomDAO.getInstance().getByName(room.getName());
            RoomDAO.getInstance().delete(room);
            setChanged();
            notifyObservers(Notifier.Message.build(MessageType.DELETE_ROOM, room.getName() + " deleted successfully"));
        }

    }
    public void setRoomsToNull() { rooms = null; }

}
