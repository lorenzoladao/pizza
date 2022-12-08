package com.example.project;

/**
 * This class creates an instance of Pizza subclasses based on the flavor provided.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class PizzaMaker {
    /**
     * create an instance of Pizza subclasses based on the flavor provided.
     *
     * @param flavor the string form of the types of pizza sold
     * @return returns the instance of Pizza subclass
     */
    public static Pizza createPizza(String flavor) {
        if (flavor.equals("pepperoni")){
            return new Pepperoni();
        }
        else if (flavor.equals("hawaiian")){
            return new Hawaiian();
        }
        else if (flavor.equals("deluxe")){
            return new Deluxe();
        }
        else {
            return null;
        }
    }
}