package com.example.project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
/**
 * This class contains all the operations that can be done on the main view of the app.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */

public class MainActivity extends AppCompatActivity {
    private StoreOrders ordersList;
    private Order order;
    private TextInputEditText numberInput;
    private static final int NUMBER_LENGTH = 10;

    /**
     * This protected method creates the view, displays the images and has all the possible selectable
     * options laid out for the user to see.
     * @param savedInstanceState the saved state when passed to this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String text = intent.getStringExtra("PHONE_NUMBER");
        numberInput = (TextInputEditText) findViewById(R.id.numberInput);

        if (text != null){
            numberInput.setText(text);
        }

        ordersList = new StoreOrders();

        ImageView myImageView = (ImageView) findViewById(R.id.imageView);
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPizzaActivity();
            }
        });

        ImageView myImageView2 = (ImageView) findViewById(R.id.deluxe);
        myImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPizzaDeluxeActivity();
            }
        });
        ImageView myImageView3 = (ImageView) findViewById(R.id.hawaiian);
        myImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPizzaHawaiianActivity();
            }
        });

        ImageView myImageView4 = (ImageView) findViewById(R.id.currentOrder);
        myImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrentActivity();
            }
        });

        ImageView myImageView5 = (ImageView) findViewById(R.id.storeOrder);
        myImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreActivity();
            }
        });

    }

    /**
     * This method allows the user to open the Pizza Activity menu from the main view.
     */
    public void openPizzaActivity(){
        numberInput = (TextInputEditText) findViewById(R.id.numberInput);
        String number = numberInput.getText().toString();
        if(checkNumber(number)) {
            Intent intent = new Intent(this, PizzaActivity.class);
            if (order == null){
                order = new Order(number);
                intent.putExtra("ORDER", order);
                pizzaActivityResultLauncher.launch(intent);
                return;
            }
            intent.putExtra("ORDER", order);
            pizzaActivityResultLauncher.launch(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Invalid Phone Number";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
            return;
        }
    }
    /**
     * This method allows the user to open the Deluxe Pizza Activity menu from the main view.
     */
    public void openPizzaDeluxeActivity(){
        numberInput = (TextInputEditText) findViewById(R.id.numberInput);
        String number = numberInput.getText().toString();
        if(checkNumber(number)) {
            Intent intent = new Intent(this, DeluxeActivity.class);
            if (order == null){
                order = new Order(number);
                intent.putExtra("ORDER", order);
                pizzaActivityResultLauncher.launch(intent);
                return;
            }
            intent.putExtra("ORDER", order);
            pizzaActivityResultLauncher.launch(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Invalid Phone Number";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
            return;
        }
    }
    /**
     * This method allows the user to open the Hawaiian Pizza Activity menu from the main view.
     */
    public void openPizzaHawaiianActivity(){
        numberInput = (TextInputEditText) findViewById(R.id.numberInput);
        String number = numberInput.getText().toString();
        if(checkNumber(number)) {
            Intent intent = new Intent(this, HawaiianActivity.class);
            if (order == null){
                order = new Order(number);
                intent.putExtra("ORDER", order);
                pizzaActivityResultLauncher.launch(intent);
                return;
            }
            intent.putExtra("ORDER", order);
            pizzaActivityResultLauncher.launch(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Invalid Phone Number";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
            return;
        }
    }

    /**
     * This method allows the user to open the current order menu from the main view.
     */
    public void openCurrentActivity(){

        String number = numberInput.getText().toString();
        if(!checkNumber(number)){
            Context context = getApplicationContext();
            CharSequence text = "Invalid Phone Number";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
            return;
        }

        Intent intent = new Intent(this, CurrentActivity.class);

        intent.putExtra("CURR_ORDER", order);
        intent.putExtra("ORDERLIST", ordersList);
        intent.putExtra("phone", number);

        currentOrderActivityResultLauncher.launch(intent);

    }

    /**
     * This method allows the user to open the Store Orders menu from the main view.
     */
    public void openStoreActivity(){

        Intent intent = new Intent(this, StoreOrderActivity.class);

        intent.putExtra("ORDERLIST", ordersList);

        storeOrderActivityResultLauncher.launch(intent);

    }

    /**
     * The activity result launcher for store orders activities, returning the store orders on return.
     */
    ActivityResultLauncher<Intent> storeOrderActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data==null)
                            return;
                        ordersList = (StoreOrders) data.getExtras().getSerializable("PIZZAORDERS");
                    }
                }
            });

    /**
     * The activity result launcher for current order activities, returning new order and the store orders on return.
     */
    ActivityResultLauncher<Intent> currentOrderActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data==null)
                            return;
                        order = (Order) data.getExtras().getSerializable("ORDER");
                        ordersList = (StoreOrders) data.getExtras().getSerializable("ORDERLIST");
                    }
                }
            });
    /**
     * The activity result launcher for all pizza activities, returning the current order on return.
     */
    ActivityResultLauncher<Intent> pizzaActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data==null)
                            return;
                        order = (Order) data.getExtras().getSerializable("ORDER");
                    }
                }
            });

    /**
     * this method checks if the user enters a valid phone number or not and converts the entered
     * value into a number.
     * @param number the phone number to be checked
     * @return false if number is invalid phone number; otherwise true
     */
    public boolean checkNumber(String number){

        if (number.isEmpty() || number.length() != NUMBER_LENGTH){
            return false;
        }
        try{
            Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
