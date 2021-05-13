package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class Controller implements Initializable {
    public TextField field;
    public String txt = "";
    public boolean open=false;

    public void AC(ActionEvent actionEvent) {
        txt = "";
        field.setText(txt);
    }

    public void delete(ActionEvent actionEvent) {
        txt = txt.substring(0, txt.length()-1);
        field.setText(txt);
    }

    public void modules(ActionEvent actionEvent) {
        txt = txt+"%";
        field.setText(txt);
    }

    public void divide(ActionEvent actionEvent) {
        txt = txt+"/";
        field.setText(txt);
    }

    public void seven(ActionEvent actionEvent) {
        txt = txt+"7";
        field.setText(txt);
    }

    public void equals(ActionEvent actionEvent) {
        System.out.println(txt);
        field.setText(String.valueOf(evaluate(txt)));
    }

    public void brackets(ActionEvent actionEvent) {
        if(!open){
            txt=txt+"(";
            field.setText(txt);
            open = true;
        }else {
            txt = txt+")";
            field.setText(txt);
            open=false;
        }
    }

    public void zero(ActionEvent actionEvent) {
        txt = txt+"0";
        field.setText(txt);
    }

    public void decimalPoint(ActionEvent actionEvent) {
        txt = txt+".";
        field.setText(txt);
    }

    public void add(ActionEvent actionEvent) {
        txt = txt+"+";
        field.setText(txt);
    }

    public void three(ActionEvent actionEvent) {
        txt = txt+"3";
        field.setText(txt);
    }

    public void two(ActionEvent actionEvent) {
        txt = txt+"2";
        field.setText(txt);
    }

    public void one(ActionEvent actionEvent) {
        txt = txt+"1";
        field.setText(txt);
    }

    public void subtract(ActionEvent actionEvent) {
        txt = txt+"-";
        field.setText(txt);
    }

    public void six(ActionEvent actionEvent) {
        txt = txt+"6";
        field.setText(txt);
    }

    public void five(ActionEvent actionEvent) {
        txt = txt+"5";
        field.setText(txt);
    }

    public void four(ActionEvent actionEvent) {
        txt = txt+"4";
        field.setText(txt);
    }

    public void multiply(ActionEvent actionEvent) {
        txt = txt+"*";
        field.setText(txt);
    }

    public void nine(ActionEvent actionEvent) {
        txt = txt+"9";
        field.setText(txt);
    }

    public void eight(ActionEvent actionEvent) {
        txt = txt+"8";
        field.setText(txt);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        field.setText("");
        field.disableProperty().setValue(Boolean.FALSE);
    }

    public int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Integer> values = new
                Stack<Integer>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new
                Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {

            // Current token is a
            // whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number,
            // push it to stack for numbers
            if (tokens[i] >= '0' &&
                    tokens[i] <= '9')
            {
                StringBuffer sbuf = new
                        StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length &&
                        tokens[i] >= '0' &&
                        tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.
                        toString()));

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                //  token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }

            // Current token is an opening brace,
            // push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered,
                // solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' ||
                    tokens[i] == '-' ||
                    tokens[i] == '*' ||
                    tokens[i] == '/')
            {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() &&
                        hasPrecedence(tokens[i],
                                ops.peek()))
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(),
                    values.pop(),
                    values.pop()));

        // Top of 'values' contains
        // result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher
    // or same precedence as 'op1',
    // otherwise returns false.
    public boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') &&
                (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an
    // operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public int applyOp(char op,
                              int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException(
                            "Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
