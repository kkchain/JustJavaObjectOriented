package com.example.android.justjavaobjectoriented;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity;

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity > 20) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 20 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 0 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked
     * add whipped chream checkbox boolean
     * add chocolate checkbox boolean
     * add user name input
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckedbox = (CheckBox) findViewById(R.id.whipped_cream_checkedbox);
        boolean hasWhippedCream = whippedCreamCheckedbox.isChecked();

        CheckBox chocolateCheckedbox = (CheckBox) findViewById(R.id.chocholate_checkedbox);
        boolean hasChocolate = chocolateCheckedbox.isChecked();

        EditText text = (EditText) findViewById(R.id.customer_name);
        String customerName = text.getText().toString();

        //pass either customer choose / not toppings
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // pass the price/hasWhippedCream/hasChocholate/customer name to pricemessage
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);
        displayMessage(priceMessage);


        // intent: open email to mail the coffee order information
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Java Coffee Order: " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            }
        }

    /**
     * Calculates the price of the order.
     * @param hasChocolate if customer add topping
     * @param hasWhippedCream if customer add topping
     */
    private int calculatePrice(boolean hasWhippedCream,boolean hasChocolate) {
        int basePrice = 2;



        // add $1 to cup of coffee for whipped cream
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        Log.v("MainActivity", "cream" + basePrice);

        // add $2 to cup of coffee for whipped cream
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        Log.v("MainActivity", "chocolate" + basePrice);
        //return total cost
        return quantity * basePrice;


    }

    /**
     * Create summary of the order
     * @param price of the order
     * @param addWhippedCream  of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String addName) {
        String priceMessage = "Name: " + addName;
        priceMessage += "\nAdd Whipped Cream = " + addWhippedCream;
        priceMessage += "\nAdd Chocolate = " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}