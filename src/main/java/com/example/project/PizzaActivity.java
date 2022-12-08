package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * This class extends the abstract Pizza class and includes specific data and operations for hawaiian pizza.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class PizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Pizza pizza;
    private EditText priceText;
    private Spinner sizeSpinner;
    private Order order;
    private Intent intent;
    private static final int MAX_TOPPING_SIZE = 7;
    private static final int MIN_TOPPING_SIZE = 1;
    private DecimalFormat df;

    /**
     * This protected method creates the view and has all the possible selectable
     * options laid out for the user to see
     * @param savedInstanceState the saved state when passed to this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        intent = getIntent();

        order = (Order) getIntent().getExtras().getSerializable("ORDER");
        ImageView pizzaImage = findViewById(R.id.pizzaImage);
        pizzaImage.setImageResource(R.drawable.pepperoni);
        CheckBox pepperoni = findViewById(R.id.pepperoni);
        pepperoni.setChecked(true);
        pizza = PizzaMaker.createPizza("pepperoni");

        priceText = findViewById(R.id.priceAmt);
        priceText.setKeyListener(null);
        df = new DecimalFormat("0.00");
        String price = "$" + df.format(pizza.price());
        priceText.setText(price);

        sizeSpinner = findViewById(R.id.sizeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);
        sizeSpinner.setOnItemSelectedListener(this);
    }

    /**
     * This method records the user choice of the pizza size and sets the price according to the
     * choice of the user.
     * @param parent The view within the AdapterView that was clicked
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String size = parent.getItemAtPosition(position).toString();
        if (size.compareTo("Small") == 0){
            pizza.setSize(Size.Small);
        }
        else if (size.compareTo("Medium") == 0){
            pizza.setSize(Size.Medium);
        }
        else if (size.compareTo("Large") == 0){
            pizza.setSize(Size.Large);
        }

        String price = "$" + df.format(pizza.price());
        priceText.setText(price);
    }

    /**
     * This method is just to check if the user selects no choice, nothing happens.
     * @param parent The AdapterView that contains no selected item
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * This method records the clicks that the user adds the pizza they have chosen to their order.
     * And displays their choice.
     * @param v The view within the Button that was clicked
     */
    public void addClick(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add Pizza to Order");
        alert.setMessage("add to order?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                order.addPizza(pizza);
                intent.putExtra("ORDER", order);
                setResult(Activity.RESULT_OK,intent);

                Context context = getApplicationContext();
                CharSequence text = "Pizza added to order.";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
                finish();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialog, int which){
                return;
                }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * This method allows asynchronous messages which allow Android components to request
     * functionality from other components of the Android system.
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        intent.putExtra("ORDER", order);
        setResult(Activity.RESULT_OK, intent);
        finish();
        return true;
    }

    /**
     * The method that contains the choice "beef" if the topping is selected by the user. As well as
     * updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void beefClick(View v){
        CheckBox beef = (CheckBox) findViewById(R.id.beef);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            beef.setChecked(false);
            return;
        }
        else {
            if (beef.isChecked()) {
                pizza.addTopping(Topping.Beef);
            } else if (!beef.isChecked()) {
                pizza.removeTopping(Topping.Beef);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "chicken" if the topping is selected by the user. As well as
     * updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void chickenClick(View v){
        CheckBox chicken = (CheckBox) findViewById(R.id.chicken);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            chicken.setChecked(false);
            return;
        }
        else {
            if (chicken.isChecked()) {
                pizza.addTopping(Topping.Chicken);
            } else if (!chicken.isChecked()) {
                pizza.removeTopping(Topping.Chicken);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "ham" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void hamClick(View v){
        CheckBox ham = (CheckBox) findViewById(R.id.ham);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            ham.setChecked(false);
            return;
        }
        else {
            if (ham.isChecked()) {
                pizza.addTopping(Topping.Ham);
            } else if (!ham.isChecked()) {
                pizza.removeTopping(Topping.Ham);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);

        }
    }

    /**
     * The method that contains the choice "pepperoni" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void pepperoniClick(View v){
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            pepperoni.setChecked(false);
            return;
        }
        else {
            if (pepperoni.isChecked()) {
                pizza.addTopping(Topping.Pepperoni);
            } else if (!pepperoni.isChecked()) {
                pizza.removeTopping(Topping.Pepperoni);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "mushroom" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void mushroomClick(View v){
        CheckBox mushroom = (CheckBox) findViewById(R.id.mushroom);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            mushroom.setChecked(false);
            return;
        }
        else {
            if (mushroom.isChecked()) {
                pizza.addTopping(Topping.Mushroom);
            } else if (!mushroom.isChecked()) {
                pizza.removeTopping(Topping.Mushroom);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "green pepper" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void gpepperClick(View v){
        CheckBox gpepper = (CheckBox) findViewById(R.id.g_pepper);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            gpepper.setChecked(false);
            return;
        }
        else {
            if (gpepper.isChecked()) {
                pizza.addTopping(Topping.GreenPepper);
            } else if (!gpepper.isChecked()) {
                pizza.removeTopping(Topping.GreenPepper);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "black olives" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void blolivesClick(View v){
        CheckBox blolives = (CheckBox) findViewById(R.id.bl_olives);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            blolives.setChecked(false);
            return;
        }
        else {
            if (blolives.isChecked()) {
                pizza.addTopping(Topping.BlackOlives);
            } else if (!blolives.isChecked()) {
                pizza.removeTopping(Topping.BlackOlives);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "onions" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void onionClick(View v){
        CheckBox onion = findViewById(R.id.onion);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            onion.setChecked(false);
            return;
        }
        else {
            if (onion.isChecked()) {
                pizza.addTopping(Topping.Onion);
            } else if (!onion.isChecked()) {
                pizza.removeTopping(Topping.Onion);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "sausage" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void sausageClick(View v) {
        CheckBox sausage = (CheckBox) findViewById(R.id.sausage);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            sausage.setChecked(false);
            return;
        }
        else{
        if (sausage.isChecked()) {
            pizza.addTopping(Topping.Sausage);
        } else if (!sausage.isChecked()) {
            pizza.removeTopping(Topping.Sausage);
        }
        String price = "$" + df.format(pizza.price());
        priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "pineapple" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void pineappleClick(View v){
        CheckBox pineapple = (CheckBox) findViewById(R.id.pineapple);
        if(pizza.toppings.size() == MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            pineapple.setChecked(false);
            return;
        }
        else {
            if (pineapple.isChecked()) {
                pizza.addTopping(Topping.Pineapple);
            } else if (!pineapple.isChecked()) {
                pizza.removeTopping(Topping.Pineapple);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * The method that contains the choice "cheese" if the topping is selected or not by the user.
     * As well as updates the price of the pizza. And makes a check of the number of toppings selected.
     * @param v The view within the CheckBox that was clicked
     */
    public void cheeseClick(View v){
        CheckBox cheese = findViewById(R.id.cheese);
        if(pizza.toppings.size() > MAX_TOPPING_SIZE){
            maxToppingsDisplay();
            cheese.setChecked(false);
            return;
        }
        else {
            if (cheese.isChecked()) {
                pizza.addTopping(Topping.Cheese);
            } else if (!cheese.isChecked()) {
                pizza.removeTopping(Topping.Cheese);
            }
            String price = "$" + df.format(pizza.price());
            priceText.setText(price);
        }
    }

    /**
     * This method displays whether the user has reached the maximum number of toppings.
     */
    public void maxToppingsDisplay(){
        Context context = getApplicationContext();
        CharSequence text = "Max Number of Toppings (7).";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }

}
