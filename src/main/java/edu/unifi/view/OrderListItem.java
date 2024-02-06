package edu.unifi.view;

import edu.unifi.controller.OrderController;
import edu.unifi.model.entities.Dish;
import edu.unifi.model.entities.OrderId;
import edu.unifi.model.entities.Table;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderListItem extends DishItem{
    JLabel quantityLabel;
    OrderId oid;

    public OrderListItem(Dish dish, int orderQuantity, OrderId oid, TableUpdateTool tableUpdateTool, Table table){
        super(dish);
        this.oid = oid;

        for (var a: deleteButton.getActionListeners())
            deleteButton.removeActionListener(a);

        for (var a: editButton.getActionListeners())
            editButton.removeActionListener(a);

        listPanel.remove(dishIdLabel);
        listPanel.remove(dishNameLabel);
        listPanel.remove(dishTypeLabel);
        listPanel.remove(actionTestPanel);
        listPanel.remove(spacer5);
        listPanel.remove(spacer6);

        listPanel.setLayout(new GridLayout(1,4));

        dishNameLabel.setText(dish.getName());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        listPanel.add(dishNameLabel);

        quantityLabel = new JLabel(String.valueOf(orderQuantity));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        listPanel.add(quantityLabel, gbc);

        float quantity = Float.parseFloat(quantityLabel.getText());
        float price = dish.getPrice();

        JLabel totalLabel = new JLabel(Float.toString(quantity*price/100));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        listPanel.add(totalLabel,gbc);

        actionTestPanel = new JPanel();
        actionTestPanel.setLayout(new GridLayout(1,2));


        editButton = new JButton();
        editButton.setHideActionText(false);
        editButton.setHorizontalAlignment(0);
        editButton.setHorizontalTextPosition(0);
        editButton.setIcon(FontIcon.of(MaterialDesignP.PENCIL, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        editButton.addActionListener(new OrderController.OrderEditController(oid, tableUpdateTool, table));
        actionTestPanel.add(editButton, gbc);


        deleteButton = new JButton();
        deleteButton.setHideActionText(false);
        deleteButton.setHorizontalAlignment(0);
        deleteButton.setHorizontalTextPosition(0);
        deleteButton.setIcon(FontIcon.of(MaterialDesignD.DELETE, 20));
        deleteButton.addActionListener(new OrderController.OrderDeletionController(oid, tableUpdateTool, table));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        actionTestPanel.add(deleteButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        listPanel.add(actionTestPanel, gbc);

    }
    public JLabel getQuantityLabel(){return quantityLabel; }
    public JLabel getDishNameLabel(){return dishNameLabel;}
}