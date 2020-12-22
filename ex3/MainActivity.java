package com.example.ex3;
//improvements to be made:
//12*4(5-3)
//9+(-3)
//trig ops along with other ops

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.lang.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_input;
    Stack<String> stack;
    ArrayList<String> post_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input=findViewById(R.id.et_input);
        stack=new Stack<String>();
        post_input=new ArrayList<String>();
        String
                ops[]={"c","del","dot","plus","minus","mul","div","percent","open","close","equal","sin","cos","log"};

        for(int i=0;i<10;i++){
            int id=getResources().getIdentifier("bt_"+Integer.toString(i),"id",getPackageName());
            findViewById(id).setOnClickListener(this);
        }
        for(int i=0;i<14;i++){
            int id=getResources().getIdentifier("bt_"+ops[i],"id",getPackageName());
            findViewById(id).setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) throws EmptyStackException{
        float result;

        switch (v.getId()){
            case R.id.bt_c:
                et_input.setText("");
                break;
            case R.id.bt_del:
                String val=et_input.getEditableText().toString();
                et_input.setText(val.substring(0,val.length()-1));
                break;
            case R.id.bt_equal:
                String ip=et_input.getEditableText().toString();
                if(!ip.startsWith("sin") && !ip.startsWith("cos") && !ip.startsWith("log")) {
                    postFix(ip);
                    try {
                        result = calc();
                        et_input.setText(String.valueOf(result));
                    }catch(EmptyStackException ex){
                        Toast.makeText(this, "INVALID. SYNTAX!", Toast.LENGTH_LONG).show();
                         makeEmpty();
                        post_input.clear();
                        //et_input.setText("");
                    }
                 }
                else
                    trig_calc(ip);
                break;
            default:
                Button bt_num=(Button)v;
                et_input.setText(et_input.getEditableText().toString() + bt_num.getText());
                break;
        }
        et_input.setSelection(et_input.getEditableText().length());
    }

    static int precedence(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case 'X':
            case '/':
            case '%':
                return 2;
        }
        return -1;
    }

    private void postFix(String ip){
        makeEmpty();
        post_input.clear();
        String num_ip;
       int prev=0;   //1 for op , 0 for other cases

        for (int i = 0; i <ip.length() ; i++) {

            char c = ip.charAt(i);
            //check if char is operator
            if(precedence(c)>0)  //operator
            {
                if(i==0)
                    post_input.add("0");   //unary  -6=0-6
                if(prev!=1)  //not op
                {
                while(stack.isEmpty()==false && precedence(stack.peek().charAt(0))>=precedence(c))
                    post_input.add(stack.pop());
                }
                else
                    post_input.add("0");
                stack.push(String.valueOf(c));
                prev=1;
            }
            else if(c==')')
            {
                char x = stack.pop().charAt(0);

                try {
                    while (x != '(') {
                        post_input.add(String.valueOf(x));
                        //result += x;
                        x = stack.pop().charAt(0);
                    }
                }catch(EmptyStackException e)
                {
                    Log.i("got", "mmmmmm-------------brac excp");
                    //stack.push("Error");
                    Log.i("got", "kkkkkk---------------"+stack.peek());
                    Log.i("got", "kkkkkk---------------"+stack.size());
                    Toast.makeText(this, "INVALID. SYNTAX!", Toast.LENGTH_LONG).show();
                     makeEmpty();
                    post_input.clear();
                    //et_input.setText("");
                }
                finally {  prev=0; }
            }
            else if(c=='(')
            {   stack.push(String.valueOf(c));    prev=0;  }

            else {
                //character is neither operator nor (
                num_ip = String.valueOf(c);
                       while ( (i + 1 != ip.length()) && ( Character.isDigit(ip.charAt(i + 1)) ||  ip.charAt(i + 1)=='.' )) {
                        num_ip = num_ip + ip.charAt(i + 1);
                        i++;
                    }

                 post_input.add(num_ip);
                 prev=0;
            }
        }
        while(!stack.isEmpty())
            post_input.add(stack.pop());
    }

    private float calc() throws EmptyStackException{
        makeEmpty();
        float res;
         Stack<Float> float_stack=new Stack<>();

        for(int i=0;i<post_input.size();i++)
        {
            String post_str=post_input.get(i);
                try {
                    float n = Float.parseFloat(post_str);
                    float_stack.push(n);
                    Log.i("err", "no: " + n);
                } catch (NumberFormatException ex) {
                    Log.i("err", "ty: " + post_str);
                    try {
                        float val1 = float_stack.pop();
                        float val2 = float_stack.pop();

                        switch (post_str) {
                            case "+":
                                float_stack.push(val2 + val1);
                                break;

                            case "-":
                                float t = val2 - val1;
                                float_stack.push(val2 - val1);
                                Log.i("err", "ty: " + t);
                                break;

                            case "/":
                                float_stack.push(val2 / val1);
                                break;

                            case "X":
                                float_stack.push(val2 * val1);
                                break;

                            case "%":
                                float_stack.push(val2 % val1);
                                break;
                        }
                    }catch(EmptyStackException exe){
                        /*Toast t = Toast.makeText(this, "INVALID - SYNTAX!", Toast.LENGTH_LONG);
                        View v = t.getView();
                        v.setBackgroundColor(Color.WHITE);
                        t.show();*/
                        Toast.makeText(this, "INVALID - SYNTAX!", Toast.LENGTH_LONG).show();
                        makeEmpty();
                        post_input.clear();
                        //et_input.setText("");
                        }
                }
                catch(EmptyStackException ex){
                     Toast.makeText(this, "INVALID .. SYNTAX!", Toast.LENGTH_LONG).show();
                      makeEmpty();
                     post_input.clear();
                     //et_input.setText("");
                }
        }

        res=float_stack.pop();
        return res;  //change return value in fn call
    }

    private void trig_calc(String ip){

        Double val = Double.parseDouble(ip.substring(3));
        if(ip.startsWith("sin"))
            et_input.setText(Double.toString(Math.sin(Math.toRadians(val))));
        else if(ip.startsWith("cos"))
            et_input.setText(Double.toString(Math.cos(Math.toRadians(val))));
        else if(ip.startsWith("log"))
            et_input.setText(Double.toString(Math.log(val)));
    }

    private void makeEmpty(){
        while (!stack.isEmpty())
            stack.pop();
    }
}