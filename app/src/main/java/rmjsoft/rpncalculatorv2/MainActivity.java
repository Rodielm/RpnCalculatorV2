package rmjsoft.rpncalculatorv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';

    private static final String MESSAGE = "Tiene menos de dos elemento para realizar la operación";
    private static final String MESSAGE2 = "Se borró todos los datos del Stack";
    private static final String MESSAGE3 = "Tiene el Stack vacío.";
    private static final String MESSAGE4 = "Se borró el ultimo elemento del Stack";


    private EditText editText;
    private TextView textView;
    private Stack<Integer> st = new Stack();

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

        switch (view.getId()){
            case R.id.btn0:
                editText.setText( editText.getText() + "0");
                break;
            case R.id.btn1:
                editText.setText( editText.getText() + "1");
                break;
            case R.id.btn2:
                editText.setText( editText.getText() + "2");
                break;
            case R.id.btn3:
                editText.setText( editText.getText() + "3");
                break;
            case R.id.btn4:
                editText.setText( editText.getText() + "4");
                break;
            case R.id.btn5:
                editText.setText( editText.getText() + "5");
                break;
            case R.id.btn6:
                editText.setText( editText.getText() + "6");
                break;
            case R.id.btn7:
                editText.setText( editText.getText() + "7");
                break;
            case R.id.btn8:
                editText.setText( editText.getText() + "8");
                break;
            case R.id.btn9:
                editText.setText( editText.getText() + "9");
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
                if(isNumber(editText.getText().toString())){
                    pushStack(st,editText.getText().toString());
                    textView.setText(st.toString());
                    editText.setText("");
                }else{
                    switchOperation(editText.getText().toString(),st);
                    textView.setText(st.toString());
                    editText.setText("");
                }
                break;
        }


    }

    private void pushStack(Stack<Integer> st, String a){
        st.push(new Integer(a));
    }

    private void popStack(Stack<Integer> st){
        if(!st.isEmpty()){
            st.pop();
            showMessage(MESSAGE4,Toast.LENGTH_SHORT);
            textView.setText(st.toString());
        }else{
            showMessage(MESSAGE3,Toast.LENGTH_SHORT);
        }
    }

    private void sumStack(Stack<Integer> st){
        int b = st.pop();
        int a = st.pop();
        st.push(a + b);
    }

    private void resStack(Stack<Integer> st){
        int b = st.pop();
        int a = st.pop();
        st.push(a - b);
    }

    private void multStack(Stack<Integer> st){
        int b = st.pop();
        int a = st.pop();
        st.push(a * b);
    }

    private void divStack(Stack<Integer> st){
        if (st.peek() != 0){
            int b = st.pop();
            int a = st.pop();
            st.push(a / b);
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

    public boolean hasMoreTwoElement(Stack<Integer> st){
        if(st.size()>1){
            return true;
        }
        return false;
    }

    public void switchOperation(String x, Stack st){

        switch (x){
            case "+":
                if(hasMoreTwoElement(st)){
                    sumStack(st);
                }else{
                    showMessage(MESSAGE,Toast.LENGTH_SHORT);
                }
                break;
            case "-":
                if(hasMoreTwoElement(st)){
                    resStack(st);
                }else{
                    showMessage(MESSAGE, Toast.LENGTH_SHORT);
                }
                break;
            case "*":
                if(hasMoreTwoElement(st)){
                    multStack(st);
                }else{
                    showMessage(MESSAGE,Toast.LENGTH_SHORT);
                }
                break;
            case "/":
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

    public void clearStack(Stack<Integer> st){
        if(!st.isEmpty()){
            st.clear();
            showMessage(MESSAGE2, Toast.LENGTH_SHORT);
            textView.setText(st.toString());
        }else{
            showMessage(MESSAGE3,Toast.LENGTH_SHORT);
        }
    }


}
