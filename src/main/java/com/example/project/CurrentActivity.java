package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;

/**
 * This class deals with the current order functionalities such as, place order and cancel order
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */
public class CurrentActivity extends AppCompatActivity{

    private ArrayList<String> pizzaliststringarray;
    private ListView listpizzas;
    private Order order;
    private StoreOrders orderList;
    private Button cancelOrder;
    private Button placeOrder;
    private  static final double NJ_SALES_TAX = 0.06625;
    private int point;
    private ArrayAdapter<String> adapter;
    private Intent intent;
    private TextView subTotal;
    private TextView salesTax;
    private TextView orderTotal;
    private TextView phoneNumber;

    /**
     *  this protected method creates the instance, and allows user to view order of the list of
     *  pizzas and allows user to also cancel order completely.
     * @param savedInstanceState the saved state when passed back to this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        point = -1;

        intent = getIntent();

        phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText(intent.getStringExtra("phone"));
        orderList = (StoreOrders) intent.getExtras().getSerializable("ORDERLIST");
        if ((Order) getIntent().getExtras().getSerializable("CURR_ORDER") == null){
            return;
        }

        order = (Order) getIntent().getExtras().getSerializable("CURR_ORDER");

        listpizzas = findViewById(R.id.listpizzas);

        pizzaliststringarray = new ArrayList<>();

        for (int i = 0; i< order.getCurrentOrders().size(); i++){
            pizzaliststringarray.add(order.getCurrentOrders().get(i).toString());
        }

        subTotal = findViewById(R.id.subTotal);
        salesTax = findViewById(R.id.salesTax);
        orderTotal = findViewById(R.id.orderTotal);

        updatePrice();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaliststringarray);
        listpizzas.setAdapter(adapter);


        cancelOrder = findViewById(R.id.buttonremove);
        placeOrder =findViewById(R.id.buttonplace);
        removeButtonListeners();
        placeButtonListener();
    }

    private void placeButtonListener(){
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pizzaliststringarray.isEmpty()){//add message
                    Context context = getApplicationContext();
                    CharSequence text = "No Pizzas in Order";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    return;
                }
                String phone = intent.getStringExtra("phone");
                order.setPhoneNumber(phone);
                orderList.addOrder(order);
                order = new Order(phone);//get phone number from prev activity

                pizzaliststringarray.clear();
                adapter.notifyDataSetChanged();
                Context context = getApplicationContext();
                CharSequence text = "Order Placed";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
                updatePrice();
            }
        });
    }

    /**
     * this method allows choices/clicks to be recorded and allows user to proceed with order query.
     */
    private void removeButtonListeners() {
        listpizzas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                listpizzas.getSelectedItem();
                point = position;
            }
        });

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pizzaliststringarray.isEmpty()){//add message
                    Context context = getApplicationContext();
                    CharSequence text = "No Pizzas in Order";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    return;
                }
                if(point < 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Choose Pizza to Delete";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    return;
                }

                order.deletePizza(point);
                pizzaliststringarray.remove(point);
                adapter.notifyDataSetChanged();
                updatePrice();
            }
        });
    }

    /**
     * this method updates the prices of the order and accordingly updates subtotal, sales tax and
     * calculates the order total and displays it.
     */
    public void updatePrice(){
        DecimalFormat df = new DecimalFormat("0.00");
        if (!order.getCurrentOrders().isEmpty()) {
            subTotal.setText(df.format(order.getSubTotal()));
            salesTax.setText(df.format(order.getSubTotal() * NJ_SALES_TAX));
            double totalAmount = order.getSubTotal() + (order.getSubTotal() * NJ_SALES_TAX);
            orderTotal.setText(df.format(totalAmount));
        }
        else{
            subTotal.setText(df.format(0));
            salesTax.setText(df.format(0));
            orderTotal.setText(df.format(0));
        }
    }


    /**
     * this method returns true once the order is complete
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        intent.putExtra("ORDER", order);
        intent.putExtra("ORDERLIST", orderList);
        setResult(Activity.RESULT_OK, intent);
        finish();
        return true;
    }

}
