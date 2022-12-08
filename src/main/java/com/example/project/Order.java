package com.example.project;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class keeps track of all items necessary to make an order and all operations that can be done to a given order
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class Order implements Serializable {
    private ArrayList<Pizza> currentOrder;
    private String phone;
    private int size;

    /**
     * takes the users phone number to create a new order
     * @param phone The phone number for the order
     */
    public Order(String phone){
        size = 0;
        this.phone = phone;
        currentOrder = new ArrayList<Pizza>();
    }

    /**
     * getter-method that returns the current order list
     * @return The current pizza list
     */
    public ArrayList<Pizza> getCurrentOrders(){
        return currentOrder;
    }

    /**
     * getter-method that returns the users phone number
     * @return the phone number of the current order
     */
    public String getPhone(){return phone; }

    /**
     * setter-method that takes in the phone number of the user
     * @param phone The phone number to change
     */
    public void setPhoneNumber(String phone){
        this.phone = phone;
    }

    /**
     * method that adds a pizza into the current order
     * @param pizza Pizza to add to order
     */
    public void addPizza(Pizza pizza){
        size++;
        currentOrder.add(pizza);
    }

    /**
     * method that removes a pizza from the current order list
     * @param index the index of the pizza
     */
    public void deletePizza(int index){
        if(size == 0){
            return;
        }
        Pizza pizza = currentOrder.get(index);
        currentOrder.remove(pizza);
        size--;

    }

    /**
     * method that clears out the current order of a user
     */
    public void deleteAll(){
        if(size == 0){
            return;
        }
        currentOrder.clear();
        size = 0;
    }

    /**
     * method that returns the subtotal of the order BEFORE tax
     * @return the subtotal of the order
     */
    public double getSubTotal(){
        double total = 0;
        if (currentOrder.size() == 0){
            return total;
        }
        for (int i = 0; i < currentOrder.size(); i++){
            total += currentOrder.get(i).price();
        }
        return total;
    }

    /**
     * method that returns the size of the order (number of items)
     * @return the size of order
     */
    public int size(){
        return size;
    }

}