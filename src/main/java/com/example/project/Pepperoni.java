package com.example.project;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This class extends the abstract Pizza class and includes specific data and operations for pepperoni pizza.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class Pepperoni extends Pizza implements Serializable {
    public static final int BASE_NUM_TOPPING = 1;
    private static final double BASE_PRICE = 8.99;

    /**
     * Create a Pepperoni object that sets the size to small and add pepperoni as topping.
     */
    public Pepperoni(){
        size = Size.Small;
        toppings.add(Topping.Pepperoni);
    }

    /**
     * Calculates the price of pepperoni pizza based on the size and number of toppings.
     *
     * @return the price of pepperoni pizza
     */
    public double price(){
        double pizzaPrice = BASE_PRICE;

        if (size == Size.Large){
            pizzaPrice += (INCR_SIZE_PRICE + INCR_SIZE_PRICE);
        }
        else if (size == Size.Medium){
            pizzaPrice += INCR_SIZE_PRICE;
        }

        if (toppings.size() > BASE_NUM_TOPPING){
            return pizzaPrice + ((toppings.size() - BASE_NUM_TOPPING) * EXTRA_TOPPINGS_PRICE);
        }
        else {
            return pizzaPrice;
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Returns textual representation of Pepperoni Pizza.
     *
     * @return String that comprises the type of pizza, the list of toppings, and the price
     */
    @Override
    public String toString(){
        return "Pepperoni pizza, " + this.toppings + ", " + df.format(this.price());
    }

}
