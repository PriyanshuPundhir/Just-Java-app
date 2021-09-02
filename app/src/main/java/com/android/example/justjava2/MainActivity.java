package com.android.example.justjava2;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField=(EditText)findViewById(R.id.name_field_view);
        String name =nameField.getText().toString();
        CheckBox wippedcream = (CheckBox) findViewById(R.id.wipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate = chocolate.isChecked();
           boolean haswippedcream = wippedcream.isChecked();
           int price = calculatePrice(haschocolate,haswippedcream);
        String priceMessage=creatOrederSummary(price,haschocolate,haswippedcream,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary of the coffee order to "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);

    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity==100)
        {
            Toast.makeText(this,"You cann't have more than 100 cupes of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1)
        {
            Toast.makeText(this,"You cann't have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private String creatOrederSummary(int price,boolean addWipedCream,boolean addChocolate, String addname){
        String priceMessage="Name : "+addname;
        priceMessage+="\n\nAdd wiped cream? "+addWipedCream;
        priceMessage+="\n\nAdd Chocolate?  "+addChocolate;

        priceMessage=priceMessage + "\n\nQuantity: " + quantity;
           priceMessage=priceMessage+"\n\nTotal: $"+price;
        priceMessage = priceMessage+"\n\nThank you !";
        return priceMessage;
    }
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice( boolean addWippedCreams,boolean addChocolates) {

           int basePrice = 50;
           if(addWippedCreams)
           {
               basePrice = basePrice + 20;
           }
           if(addChocolates)
           {
               basePrice = basePrice + 10;
           }

        return quantity * basePrice;
    }
}