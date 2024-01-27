package edu.unifi;

import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.view.DishCreationTool;
import edu.unifi.view.Home;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class Notifier implements Observer {
    private volatile static Notifier instance = null;
    private Home home;

    public static Notifier getInstance() throws Exception {
        Notifier thisInstance = instance;
        if (instance == null) {
            synchronized (DishCreationTool.class) {
                if (thisInstance == null)
                    instance = thisInstance = new Notifier();
            }
        }
        return thisInstance;
    }

    @Override
    public void update(Observable o, Object toDisplay) {
        if (toDisplay.equals("TableAdded")) {
            home.showAddedTableDialog();
            home.showTables();
        }
        if(toDisplay.equals("TableDeleted")){
            home.showDeletedTableDialog();
            home.showTables();
        }
    }

    public void setHome(Home home) {
        this.home = home;
    }
}
