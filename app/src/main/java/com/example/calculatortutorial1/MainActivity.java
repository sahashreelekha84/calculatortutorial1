package com.example.calculatortutorial1;
import androidx.appcompat.app.AppCompatActivity;

import android.media.VolumeShaper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private StringBuilder currentInput;
    private double firstNumber ;
    private String operator = "";

    private boolean isNewInput = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);
        currentInput = new StringBuilder();

        // Define click listeners for digit buttons (0-9)
        int[] digitButtonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
                R.id.btn_8, R.id.btn_9
        };

        for (int id : digitButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDigitButtonClick(button.getText().toString());
                }
            });
        }

        // Define click listeners for operator buttons (+, -, *, /)
        int[] operatorButtonIds = {
                R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onOperatorButtonClick(button.getText().toString());
                }
            });
        }

        // Define click listener for the equal button (=)
        Button equalButton = findViewById(R.id.btn_equal);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEqualButtonClick();
            }
        });

        // Define click listener for the clear button (C)
        Button clearButton = findViewById(R.id.btn_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClearButtonClick();
            }
        });

        // Define click listener for the decimal point button (.)
        Button dotButton = findViewById(R.id.btn_dot);
        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDotButtonClick();
            }
        });
    }

    private void onDigitButtonClick(String digit) {
        if (isNewInput) {
            screen.setText(digit);
            isNewInput = false;
        } else {
            screen.append(digit);
        }
        currentInput.append(digit);
    }


    private void onOperatorButtonClick(String newOperator) {
        if (operator.isEmpty()) {
            // If there is no previous operator, simply set the new operator and store the current input.
            operator = newOperator;
            firstNumber = Double.parseDouble(currentInput.toString());
            isNewInput = true;
        } else {
            // If there is a previous operator, calculate the result based on the current operator.
            calculateResult();
            operator = newOperator;
            isNewInput = true;
        }
        screen.setText(String.valueOf(firstNumber) + " " + operator); // Display the first number and operator
        currentInput.setLength(0); // Clear the current input
    }


    private void onEqualButtonClick() {
        if (!isNewInput) {
            calculateResult();
            isNewInput = true;
            operator = "";
        }
    }

    private void onClearButtonClick() {
        screen.setText("0");
        currentInput.setLength(0);
        firstNumber = 0;
        operator = "";
        isNewInput = true;
    }

    private void onDotButtonClick() {
        if (isNewInput) {
            screen.setText("0.");
            currentInput.setLength(0);
        } else if (currentInput.indexOf(".") == -1) {
            screen.append(".");
            currentInput.append(".");
        }
        isNewInput = false;
    }

    private void calculateResult() {
        double secondNumber = Double.parseDouble(currentInput.toString());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    screen.setText("Error");
                    return; // Exit early to avoid further calculations
                }
                break;
        }
        firstNumber = result;
        screen.setText(String.valueOf(result));
         // Update firstNumber with the result for future calculations

    }


}
