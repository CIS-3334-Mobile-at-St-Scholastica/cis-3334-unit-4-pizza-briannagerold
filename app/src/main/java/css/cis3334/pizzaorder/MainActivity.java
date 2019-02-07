package css.cis3334.pizzaorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements updateViewInterface {

    RadioButton rbSmall;
    RadioButton rbMedium;
    RadioButton rbLarge;
    CheckBox chkbxCheese;
    CheckBox chkbxDelivery;
    TextView txtTotal;
    TextView txtStatus;
    TextView txtPizzasOrdered;
    Spinner spinnerToppings;

    PizzaOrderInterface pizzaOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up our radio buttons
        rbSmall = (RadioButton) findViewById(R.id.radioButtonSmall);
        rbMedium = (RadioButton) findViewById(R.id.radioButtonMedium);
        rbLarge = (RadioButton) findViewById(R.id.radioButtonLarge);

        // Set up the Check Boxes
        chkbxCheese = (CheckBox) findViewById(R.id.checkBoxCheese);
        chkbxDelivery = (CheckBox) findViewById(R.id.checkBoxDeluvery);

        // Set up the TextViews
        txtTotal = (TextView) findViewById(R.id.textViewTotal);
        txtStatus = (TextView) findViewById(R.id.textViewStatus);
        txtPizzasOrdered = (TextView) findViewById(R.id.textViewPizzasOrdered);
        // Set up the Spinner
        spinnerToppings = (Spinner) findViewById(R.id.spinnerToppings);

        pizzaOrder = new PizzaOrder(this);

        rbSmall.append(" -- $" + pizzaOrder.getPrice(Pizza.pizzaSize.SMALL));
        rbMedium.append(" -- $" + pizzaOrder.getPrice(Pizza.pizzaSize.MEDIUM));
        rbLarge.append(" -- $" + pizzaOrder.getPrice(Pizza.pizzaSize.LARGE));

        rbSmall.setChecked(true);
    }

    @Override
    public void updateOrderStatusInView(String orderStatus) {

        txtStatus.setText("Order Status: " + orderStatus);
    }

    public void onClickOrder(View view) {
        // ****** For the Assignment, students need to add code here to get information from the UI widgets...
        //set if it is a delivery order
        pizzaOrder.setDelivery(chkbxDelivery.isChecked());

        //do they want extra cheese?
        boolean extraCheese = false;
        if(chkbxCheese.isChecked()){
            extraCheese = true;
        }

        //get the size of the pizza
        String pizzaSize = "small";
        if(rbMedium.isChecked()){
            pizzaSize = "medium";
        }
        else if (rbLarge.isChecked()){
            pizzaSize = "large";
        }

        //get the topping for the spinner
        String pizzaTopping = spinnerToppings.getSelectedItem().toString();

        // ****** For the Practice Activity, students need to call to OrderPizza here
        //String orderDescription = pizzaOrder.OrderPizza("Peperoni", "large", false);
        // ****** For the Assignment, students will modify the order to fit the type of pizza the user selects using the UI widgets

        //order the selected pizza
        String orderDescription = pizzaOrder.OrderPizza(pizzaTopping, pizzaSize, extraCheese);

        //display a pop up message for a long period of time
        Toast.makeText(getApplicationContext(), "You have ordered a "+orderDescription , Toast.LENGTH_LONG).show();
        // add this pizza to the textview the lists the pizzas
        txtPizzasOrdered.append(orderDescription+"\n");

        //set the text field for the total due
        txtTotal.setText("Total Due: " + pizzaOrder.getTotalBill().toString());
    }
}
