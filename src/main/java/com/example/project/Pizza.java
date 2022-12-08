package com.example.project;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is an abstract class that defines the common data items and operations for all instances of pizza.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public abstract class Pizza implements Serializable {
    protected static final double EXTRA_TOPPINGS_PRICE = 1.49;
    protected static final double INCR_SIZE_PRICE = 2.00;
    protected ArrayList<Topping> toppings = new ArrayList<Topping>();
    protected Size size;

    /**
     * Adds a new topping to the toppings ArrayList.
     * @param topping topping to be added
     */
    public void addTopping(Topping topping){
        toppings.add(topping);
    }

    /**
     * Removes a topping from the toppings ArrayList.
     * @param topping topping to be added
     */
    public void removeTopping(Topping topping){
        toppings.remove(topping);
    }

    /**
     * Returns the ArrayList that stores all the toppings.
     * @return The ArrayList that contains all toppings
     */
    public ArrayList<Topping> getToppingList(){
        return toppings;
    }

    /**
     * Sets the variable size to the provided size.
     * @param size The size that is changed to provided by user.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Abstract method that returns price of pizza.
     * @return price of pizza
     */
    public abstract double price();

    /**
     * Abstract method that returns the representation of a Pizza instance to a String
     * @return the representation of Pizza as a String
     */
    @Override
    public abstract String toString();
}
