package com.example.project;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This class extends the abstract Pizza class and includes specific data and operations for deluxe pizza.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class Deluxe extends Pizza implements Serializable {
    public static final int BASE_NUM_TOPPING = 5;
    private static final double BASE_PRICE = 12.99;

    /**
     * Create a Deluxe object that sets the size to small and add pepperoni, sausage, green pepper, mushroom,
     * onion as toppings.
     */
    public Deluxe(){
        size = Size.Small;
        toppings.add(Topping.Pepperoni);
        toppings.add(Topping.Sausage);
        toppings.add(Topping.GreenPepper);
        toppings.add(Topping.Mushroom);
        toppings.add(Topping.Onion);
    }

    /**
     * Calculates the price of pepperoni pizza based on the size and number of toppings.
     *
     * @return the price of deluxe pizza
     */
    public double price(){
        double pizzaPrice = BASE_PRICE;

        if (size == Size.Large){
            pizzaPrice += (INCR_SIZE_PRICE + INCR_SIZE_PRICE);
        }
        else if (size == Size.Medium){
            pizzaPrice += INCR_SIZE_PRICE;
        }

        DecimalFormat form = new DecimalFormat("#,##0.00");
        if (toppings.size() > BASE_NUM_TOPPING){
            return pizzaPrice + ((toppings.size() - BASE_NUM_TOPPING) * EXTRA_TOPPINGS_PRICE);
        }
        else {
            return pizzaPrice;
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");
    /**
     * Returns textual representation of Deluxe Pizza.
     *
     * @return String that comprises the type of pizza, the list of toppings, and the price
     */
    @Override
    public String toString(){
        return "Deluxe pizza, " + this.toppings + ", " + df.format(this.price());
    }

}
