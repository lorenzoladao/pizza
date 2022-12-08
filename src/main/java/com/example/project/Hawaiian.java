package com.example.project;

import java.io.Serializable;
import java.text.DecimalFormat;


/**
 * This class extends the abstract Pizza class and includes specific data and operations for hawaiian pizza.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class Hawaiian extends Pizza implements Serializable {
    public static final int BASE_NUM_TOPPING = 2;
    private static final double BASE_PRICE = 10.99;

    /**
     * Create a Pepperoni object that sets the size to small and add pineapple and ham as toppings.
     */
    public Hawaiian(){
        size = Size.Small;
        toppings.add(Topping.Pineapple);
        toppings.add(Topping.Ham);
    }

    /**
     * Calculates the price of pepperoni pizza based on the size and number of toppings.
     *
     * @return the price of hawaiian pizza
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
     * Returns textual representation of Hawaiian Pizza.
     *
     * @return String that comprises the type of pizza, the list of toppings, and the price
     */
    @Override
    public String toString(){
        return "Hawaiian pizza, " + this.toppings + ", " + df.format(this.price());
    }

}
