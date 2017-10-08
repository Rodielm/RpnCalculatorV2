package rmjsoft.rpncalculatorv2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.##");

    private static final String MESSAGE = "No contiene suficiente elemento para realizar la operación";
    private static final String MESSAGE2 = "Se borró todos los datos del Stack";
    private static final String MESSAGE3 = "Tiene el Stack vacío.";
    private static final String MESSAGE4 = "Se borró el ultimo elemento del Stack";
    private static final String MESSAGE5 = "Favor introducir elemento numericos antes de un operador";


    private double firstOperation = Double.NaN;
    private double secondOperation = Double.NaN;
    private String result;

    private EditText editText;
    private TextView textView;

    @SuppressWarnings("unchecked")
    private Stack<String> st = new Stack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showHelp();

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
        findViewById(R.id.btnSpace).setOnClickListener(this);

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
        editText.setMovementMethod(new ScrollingMovementMethod());
        textView = (TextView) findViewById(R.id.txtStack);
        textView.setMovementMethod(new ScrollingMovementMethod());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("SetTextI18n")
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
                if (editText.getText().length() > 0) {
                    if(specialDecimalCheck()){
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
                specialCheck();
                editText.setText(editText.getText() + "+ ");
                break;
            case R.id.btnRes:
                specialCheck();
                editText.setText(editText.getText() + "- ");
                break;
            case R.id.btnX:
                specialCheck();
                editText.setText(editText.getText() + "* ");
                break;
            case R.id.btnD:
                specialCheck();
                editText.setText(editText.getText() + "/ ");
                break;
            case R.id.btnE:
                computeMath(editText.getText().toString());
                break;
            case R.id.btnSpace:
                if(specialSpaceCheck()){
                editText.setText(editText.getText() + " ");
                }
                break;
        }
    }

    //
    @SuppressWarnings("unchecked")
    private void pushStack(Stack st, String a) {
        String decimal = formatDecimal(a);
        st.push(decimal);
    }

    private String formatDecimal(String number) {
        double num = Double.parseDouble(number);
        return decimalFormat.format(num);
    }

    private void popStack(Stack st) {
        if (!st.isEmpty()) {
            st.pop();
            showMessage(MESSAGE4);
            textView.setText(st.toString());
        } else {
            showMessage(MESSAGE3);
        }
    }

    private void sumStack(Stack<String> st) {
        secondOperation = Double.parseDouble(st.pop());
        firstOperation = Double.parseDouble(st.pop());
        result = formatDecimal(String.valueOf(secondOperation + firstOperation));
        st.push(result);
    }

    private void resStack(Stack<String> st) {
        secondOperation = Double.parseDouble(st.pop());
        firstOperation = Double.parseDouble(st.pop());
        result = formatDecimal(String.valueOf(firstOperation - secondOperation));
        st.push(result);
    }

    private void multStack(Stack<String> st) {
        secondOperation = Double.parseDouble(st.pop());
        firstOperation = Double.parseDouble(st.pop());
        result = formatDecimal(String.valueOf(firstOperation * secondOperation));
        st.push(result);
    }

    private void divStack(Stack<String> st) {
        if (!st.peek().equals("0")) {
            secondOperation = Double.parseDouble(st.pop());
            firstOperation = Double.parseDouble(st.pop());
            result = formatDecimal(String.valueOf(firstOperation / secondOperation));
            st.push(result);
        } else {
            showMessage("Error: No se puede dividir con 0, intente con otro operador o elimine el elemento");
        }
    }

    private boolean isNumber(String x) {
        boolean flag = false;
        if (!x.isEmpty()) {
            flag = TextUtils.isDigitsOnly(x);
        }
        return flag;
    }

    private boolean isDecimal(String text) {
        boolean flag = false;
        if (!text.isEmpty()) {
            if (text.contains(".")) {
                if (isNumber(text.replace(".", ""))) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private boolean hasMoreTwoElement(Stack<String> st) {
        return st.size() > 1;
    }

    @SuppressWarnings("unchecked")
    private void switchOperation(String x, Stack st) {

        switch (x) {
            case ADDITION:
                sumStack(st);
                textView.setText(st.toString());
                editText.setText("");
                break;
            case SUBTRACTION:
                resStack(st);
                textView.setText(st.toString());
                editText.setText("");
                break;
            case MULTIPLICATION:
                multStack(st);
                textView.setText(st.toString());
                editText.setText("");
                break;
            case DIVISION:
                divStack(st);
                textView.setText(st.toString());
                editText.setText("");
                break;
            default:
                Toast.makeText(this, "Operación invalida", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearStack(Stack<String> st) {
        if (!st.isEmpty()) {
            st.clear();
            showMessage(MESSAGE2);
            textView.setText(st.toString());
        } else {
            showMessage(MESSAGE3);
        }
    }

    private void computeMath(String number) {
        String[] numb = number.split(" ");

        if (checkValues(st.size(), numb)) {
            for (String num : numb) {
                if (isDecimal(num)) {
                    pushStack(st, num);
                    textView.setText(st.toString());
                    editText.setText("");
                } else if (isNumber(num)) {
                    pushStack(st, num);
                    textView.setText(st.toString());
                    editText.setText("");
                } else {
                    if (hasMoreTwoElement(st)) {
                        switchOperation(num, st);

                    } else {
                        showMessage(MESSAGE5);
                        break;
                    }
                }
            }
        } else {
            showMessage(MESSAGE);
        }

    }

    private boolean checkValues(Integer stSize, String[] input) {

        int resultInput = 0;
        int OperatorCount = 0;

        for (String element : input) {
            if (isNumber(element)) {
                resultInput = resultInput + 1;
            } else if (isDecimal(element)) {
                resultInput = resultInput + 1;
            } else {
                OperatorCount = OperatorCount + 1;
            }
        }

        resultInput = resultInput + stSize;

        return OperatorCount < resultInput;
    }

    private void showHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ayuda");
        builder.setMessage("C: Para limpiar la entrada\n" +
                "R: Para empezar desde cero\n" +
                "P: Para eliminar el último elemento agregado\n" +
                "_ :Para introducir espacio entre dos elementos\n" +
                "↵:Para realizar Operaciones");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog help = builder.create();
        help.show();
    }

    private boolean specialSpaceCheck(){
        boolean flag = false;
        int inputSize = editText.getText().length();
        if(inputSize > 0) {
            String lastElement = editText.getText().subSequence(inputSize - 1, inputSize).toString();
            if (!lastElement.contains(" ")) {
                flag = true;
            }
        }
        return flag;
    }

    private boolean specialDecimalCheck() {
        boolean flag = false;
        String[] numb = editText.getText().toString().split(" ");
        String lastElement = numb[numb.length-1];
        if (isNumber(lastElement)) {
            flag = true;
        }
        return flag;
    }

    private void specialCheck() {

        int inputSize = editText.getText().length();
        if (inputSize > 0) {
            String lastElement = editText.getText().subSequence(inputSize - 1, inputSize).toString();
            if (!isNumber(lastElement) && !isDecimal(lastElement) && !lastElement.contains(" ")) {
                editText.setText(editText.getText() + " ");
            } else if (isNumber(lastElement) || isDecimal(lastElement)) {
                editText.setText(editText.getText() + " ");
            }
        }
    }
}
