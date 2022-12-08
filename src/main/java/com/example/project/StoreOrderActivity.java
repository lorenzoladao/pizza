package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * This class allows the user to view all of the orders that the store receives from specific phone
 * numbers.
 *
 * @author Asif Haque, Margaret Knoebel, Lorenzo Ladao
 */

public class StoreOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private ListView listView;
    private StoreOrders orderList;
    private Button cancelOrder;
    private int point;
    private ArrayAdapter<String> adapter;
    private  Intent intent;
    private TextView TotalMoney;
    private  static final double NJ_SALES_TAX = 0.06625;

    /**
     *  this protected method creates the instance, and allows user to view the list of orders
     *  and allows user to also cancel order completely from the list.
     * @param savedInstanceState the saved state when passed to this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        point = 0;

        spinner = findViewById(R.id.spinner);

        TotalMoney = findViewById(R.id.TotalMoney);

        intent = getIntent();

        orderList = (StoreOrders) intent.getExtras().getSerializable("ORDERLIST");

        ArrayList<String> phoneNumbers = new ArrayList<>();
        for(int i = 0; i < orderList.size(); i++){
            phoneNumbers.add(orderList.getOrders().get(i).getPhone());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, phoneNumbers);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        cancelOrder = findViewById(R.id.cancelOrder);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderList.size() == 0){
                    Context context = getApplicationContext();
                    CharSequence text = "No Orders Left";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    return;
                }
                if(orderList.getOrders().get(point) == null)
                    return;
                phoneNumbers.remove(point);
                orderList.deleteOrder(point);
                adapter.notifyDataSetChanged();
                update();
                Context context = getApplicationContext();
                CharSequence text = "Order Canceled";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        });

    }

    /**
     * This method updates the number of orders that are in the store's list of orders. And updates
     * the list view with an updated list of orders.
     */
    public void update(){
        if(orderList.size() == 0){
            listView.setAdapter(null);
            return;
        }

        listView = findViewById(R.id.orders);
        spinner.setSelection(0);
        ArrayList<Pizza> p = orderList.getOrders().get(0).getCurrentOrders();

        String[] pizzas = new String[p.size()];
        ArrayList<String> piz = new ArrayList<>();
        for(int i = 0; i < p.size(); i++){
            pizzas[i] = p.get(i).toString();
            piz.add(p.get(i).toString());
        }
        ArrayAdapter<String> pizzaArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, piz);
        listView.setAdapter(pizzaArrayAdapter);
    }

    /**
     * This method records the user choice of the pizza orders and sets the price according to the
     * entire order.
     * @param parent The view within the AdapterView that was clicked
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id){//
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);

        listView = findViewById(R.id.orders);

        ArrayList<Pizza> p = orderList.getOrders().get(position).getCurrentOrders();
        point = position;
        String[] pizzas = new String[p.size()];
        ArrayList<String> piz = new ArrayList<>();
        for(int i = 0; i < p.size(); i++){
            pizzas[i] = p.get(i).toString();
            piz.add(p.get(i).toString());
        }
        ArrayAdapter<String> pizzaArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, piz);
        listView.setAdapter(pizzaArrayAdapter);

        DecimalFormat df = new DecimalFormat("0.00");
        double money = orderList.getOrders().get(position).getSubTotal() + orderList.getOrders().get(position).getSubTotal() * NJ_SALES_TAX;
        TotalMoney.setText(df.format(money));
    }

    /**
     * This method is just to check if the user selects no choice, nothing happens.
     * @param parent The AdapterView that contains no selected item
     */
    @Override
    public void onNothingSelected(AdapterView parent){
    }

    /**
     * this method messages which allow Android components to request functionality
     * from other components with extra data and returns true.
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        intent.putExtra("PIZZAORDERS", orderList);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

}
