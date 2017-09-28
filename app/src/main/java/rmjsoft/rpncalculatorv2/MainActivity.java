package rmjsoft.rpncalculatorv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ADDITION = "+";
    private static final String SUBTRACTION = "-";
    private static final String MULTIPLICATION = "*";
    private static final String DIVISION = "/";
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.#");

    private static final String MESSAGE = "Tiene menos de dos elemento para realizar la operación";
    private static final String MESSAGE2 = "Se borró todos los datos del Stack";
    private static final String MESSAGE3 = "Tiene el Stack vacío.";
    private static final String MESSAGE4 = "Se borró el ultimo elemento del Stack";

    private double firstOperation = Double.NaN;
    private double secondOperation = Double.NaN;
    private String result;

    private EditText editText;
    private TextView textView;
    private Stack<String> st = new Stack();

    private char CURRENT_ACTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Numbers
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnPeriod).setOnClickListener(this);

        // Commands
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnD).setOnClickListener(this);
        findViewById(R.id.btnR).setOnClickListener(this);
        findViewById(R.id.btnE).setOnClickListener(this);
        findViewById(R.id.btnP).setOnClickListener(this);

        //Operation
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnRes).setOnClickListener(this);
        findViewById(R.id.btnX).setOnClickListener(this);
        findViewById(R.id.btnD).setOnClickListener(this);

        //Componentes
        editText = (EditText) findViewById(R.id.txtEdit);
        textView = (TextView) findViewById(R.id.txtStack);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn0:
                editText.setText(editText.getText() + "0");
                break;
            case R.id.btn1:
                editText.setText(editText.getText() + "1");
                break;
            case R.id.btn2:
                editText.setText(editText.getText() + "2");
                break;
            case R.id.btn3:
                editText.setText(editText.getText() + "3");
                break;
            case R.id.btn4:
                editText.setText(editText.getText() + "4");
                break;
            case R.id.btn5:
                editText.setText(editText.getText() + "5");
                break;
            case R.id.btn6:
                editText.setText(editText.getText() + "6");
                break;
            case R.id.btn7:
                editText.setText(editText.getText() + "7");
                break;
            case R.id.btn8:
                editText.setText(editText.getText() + "8");
                break;
            case R.id.btn9:
                editText.setText(editText.getText() + "9");
                break;
            case R.id.btnPeriod:
                if (editText.getText().length() > 0){
                        if(!editText.getText().toString().contains(".")) {
                            editText.setText(editText.getText() + ".");
                        }
                }
                break;
            case R.id.btnC:
                editText.setText("");
                break;
            case R.id.btnR:
                clearStack(st);
                break;
            case R.id.btnP:
                popStack(st);
                break;
            case R.id.btnPlus:
                editText.setText(editText.getText() + "+");
                break;
            case R.id.btnRes:
                editText.setText(editText.getText() + "-");
                break;
            case R.id.btnX:
                editText.setText(editText.getText() + "*");
                break;
            case R.id.btnD:
                editText.setText(editText.getText() + "/");
                break;
            case R.id.btnE:
                if(isDecimal(editText.getText().toString())) {
                    pushStack(st, editText.getText().toString());
                    textView.setText(st.toString());
                    editText.setText("");
                }else if(isNumber(editText.getText().toString())){
                    pushStack(st, editText.getText().toString());
                    textView.setText(st.toString());
                    editText.setText("");
                    }
                else{
                    switchOperation(editText.getText().toString(),st);
                    textView.setText(st.toString());
                    editText.setText("");
                }
                break;
        }


    }
//
    private void pushStack(Stack st, String a){
        String decimal = formatDecimal(a);
        st.push(decimal);
    }

    private String formatDecimal(String number){
        double num = Double.parseDouble(number);
        String decimal = decimalFormat.format(num);
        return decimal;
    }

    private void popStack(Stack st){
        if(!st.isEmpty()){
            st.pop();
            showMessage(MESSAGE4,Toast.LENGTH_SHORT);
            textView.setText(st.toString());
        }else{
            showMessage(MESSAGE3,Toast.LENGTH_SHORT);
        }
    }

    private void sumStack(Stack<String> st){
        secondOperation = Double.parseDouble(st.pop());
        firstOperation = Double.parseDouble(st.pop());
        result = formatDecimal(String.valueOf(secondOperation + firstOperation));
        st.push(result);
    }

    private void resStack(Stack<String> st){
        secondOperation = Double.parseDouble(st.pop());
        firstOperation = Double.parseDouble(st.pop());
        result = String.valueOf(firstOperation - secondOperation);
        st.push(result);
    }

    private void multStack(Stack<String> st){
        secondOperation = Double.parseDouble(st.pop());
        firstOperation = Double.parseDouble(st.pop());
        result = String.valueOf(firstOperation * secondOperation);
        st.push(result);
    }

    private void divStack(Stack<String> st){
        if (st.peek() != "0"){
            secondOperation = Double.parseDouble(st.pop());
            firstOperation = Double.parseDouble(st.pop());
            result = String.valueOf(firstOperation / secondOperation);
            st.push(result);
        }else{
            showMessage("Error: No se puede dividir con 0, intente con otro operador o elimine el elemento",Toast.LENGTH_SHORT);
        }
    }

    private boolean isNumber(String x){
        boolean flag = false;
        if(!x.isEmpty()){
            flag = TextUtils.isDigitsOnly(x);
        }
        return flag;
    }

    public boolean isDecimal(String text){
        boolean flag = false;
        if(!text.isEmpty()) {
            if (text.contains(".")) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean hasMoreTwoElement(Stack<String> st){
        if(st.size()>1){
            return true;
        }
        return false;
    }

    public void switchOperation(String x, Stack st){

        switch (x){
            case ADDITION:
                if(hasMoreTwoElement(st)){
                    sumStack(st);
                }else{
                    showMessage(MESSAGE,Toast.LENGTH_SHORT);
                }
                break;
            case SUBTRACTION:
                if(hasMoreTwoElement(st)){
                    resStack(st);
                }else{
                    showMessage(MESSAGE, Toast.LENGTH_SHORT);
                }
                break;
            case MULTIPLICATION:
                if(hasMoreTwoElement(st)){
                    multStack(st);
                }else{
                    showMessage(MESSAGE,Toast.LENGTH_SHORT);
                }
                break;
            case DIVISION:
                if(hasMoreTwoElement(st)){
                    divStack(st);
                }else{
                    showMessage(MESSAGE,Toast.LENGTH_SHORT);
                }
                break;
            default:
                Toast.makeText(this, "Comando invalido", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void showMessage(String message, int duration){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void clearStack(Stack<String> st){
        if(!st.isEmpty()){
            st.clear();
            showMessage(MESSAGE2, Toast.LENGTH_SHORT);
            textView.setText(st.toString());
        }else{
            showMessage(MESSAGE3,Toast.LENGTH_SHORT);
        }
    }


}
