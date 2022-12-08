package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class contains the operations that are able to be completed in the store orders menu,
 * add an order, delete an order and to import orders from a file.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class StoreOrders implements Serializable {
    private ArrayList<Order> storeOrder;
    private int size;

    /**
     * method that creates the list in which all store orders will be stored
     *
     */
    public StoreOrders(){
        size = 0;
        storeOrder = new ArrayList<>();
    }

    /**
     * method that returns orders from the total list
     *
     * @return the whole store order
     */
    public ArrayList<Order> getOrders(){
        return storeOrder;
    }

    /**
     * method the reads in a file from the user that contains the phone number, cost of order,
     * and which pizzas are selected, then finally closes the file
     *
     * @param file file to export to
     * @param order the whole order
     * @return the exported file
     * @throws FileNotFoundException error if file not found
     */
    public File export(File file, ArrayList<Order> order) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(file);
        for(int i = 0; i < order.size(); i++){
            pw.println("Phone Number: " + order.get(i).getPhone());
            DecimalFormat df = new DecimalFormat("0.00");
            String moneyFormat = df.format(order.get(i).getSubTotal() + order.get(i).getSubTotal() * .06625);
            pw.println("Total: " + moneyFormat);
            pw.print("Pizzas: ");
            for(int j = 0; j < order.get(i).getCurrentOrders().size(); j++) {
                pw.println(order.get(i).getCurrentOrders().get(j));
            }
        }

        pw.close();
        return file;

    }

    /**
     * method that adds an order to the list of total store orders
     *
     * @param order the order to add to store order
     * @return 0 if contains order, otherwise 1
     */
    public int addOrder(Order order){
        if(storeOrder.contains(order)){
            return 0;
        }
        size++;
        storeOrder.add(order);
        return 1;
    }

    /**
     * method that removes an order from the list of store orders
     *
     * @param index the index of the order
     */
    public void deleteOrder(int index){
        if(size == 0){
            return;
        }
        Order order = storeOrder.get(index);
        storeOrder.remove(order);
        size--;
    }

    /**
     * method that increases the size of the list of total store orders
     *
     * @return size of store orders
     */
    public int size(){
        return size;
    }
}
