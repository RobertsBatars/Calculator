package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText txtResult;
    private double firstNumber = 0;
    private String operation = "";
    private boolean isNewNumber = true;
    private double memoryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        txtResult.setText("0");

        // Initialize number buttons
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : numberIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleNumber(((Button) v).getText().toString());
                }
            });
        }

        // Set up operation buttons
        setupOperationButton(R.id.btnPlus, "+");
        setupOperationButton(R.id.btnMinus, "-");
        setupOperationButton(R.id.btnMultiply, "*");
        setupOperationButton(R.id.btnDivide, "/");

        // Set up equals button
        findViewById(R.id.btnEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        // Set up clear buttons
        findViewById(R.id.btnC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });

        findViewById(R.id.btnCE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtResult.setText("0");
                isNewNumber = true;
            }
        });

        // Set up backspace button
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackspace();
            }
        });

        // Set up sign change button
        findViewById(R.id.btnPlusMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignChange();
            }
        });

        // Set up square root button
        findViewById(R.id.btnSqrt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSquareRoot();
            }
        });

        // Set up decimal button
        findViewById(R.id.btnDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDecimal();
            }
        });

        // Set up reciprocal button
        findViewById(R.id.btnReciprocal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReciprocal();
            }
        });

        // Set up memory buttons
        findViewById(R.id.btnMC).setOnClickListener(v -> handleMemoryOperation("MC"));
        findViewById(R.id.btnMR).setOnClickListener(v -> handleMemoryOperation("MR"));
        findViewById(R.id.btnMS).setOnClickListener(v -> handleMemoryOperation("MS"));
        findViewById(R.id.btnMPlus).setOnClickListener(v -> handleMemoryOperation("M+"));
        findViewById(R.id.btnMMinus).setOnClickListener(v -> handleMemoryOperation("M-"));
    }

    private void setupOperationButton(int buttonId, final String operationType) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation(operationType);
            }
        });
    }

    private void handleNumber(String number) {
        if (isNewNumber) {
            txtResult.setText(number);
            isNewNumber = false;
        } else {
            txtResult.append(number);
        }
    }

    private void handleOperation(String op) {
        firstNumber = Double.parseDouble(txtResult.getText().toString());
        operation = op;
        isNewNumber = true;
    }

    private void calculateResult() {
        if (!operation.isEmpty()) {
            double secondNumber = Double.parseDouble(txtResult.getText().toString());
            double result = 0;

            switch (operation) {
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
                        txtResult.setText("Error");
                        return;
                    }
                    break;
            }

            txtResult.setText(String.valueOf(result));
            operation = "";
            isNewNumber = true;
        }
    }

    private void clearAll() {
        txtResult.setText("0");
        firstNumber = 0;
        operation = "";
        isNewNumber = true;
    }

    private void handleBackspace() {
        String currentText = txtResult.getText().toString();
        if (currentText.length() > 1) {
            txtResult.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            txtResult.setText("0");
            isNewNumber = true;
        }
    }

    private void handleSignChange() {
        double value = Double.parseDouble(txtResult.getText().toString());
        txtResult.setText(String.valueOf(-value));
    }

    private void handleSquareRoot() {
        double value = Double.parseDouble(txtResult.getText().toString());
        if (value >= 0) {
            txtResult.setText(String.valueOf(Math.sqrt(value)));
        } else {
            txtResult.setText("Error");
        }
        isNewNumber = true;
    }

    private void handleDecimal() {
        if (isNewNumber) {
            txtResult.setText("0.");
            isNewNumber = false;
        } else if (!txtResult.getText().toString().contains(".")) {
            txtResult.append(".");
        }
    }

    private void handleReciprocal() {
        double value = Double.parseDouble(txtResult.getText().toString());
        if (value != 0) {
            txtResult.setText(String.valueOf(1 / value));
        } else {
            txtResult.setText("Error");
        }
        isNewNumber = true;
    }

    private void handleMemoryOperation(String operation) {
        double currentValue = Double.parseDouble(txtResult.getText().toString());
        switch (operation) {
            case "MC":
                memoryValue = 0;
                break;
            case "MR":
                txtResult.setText(String.valueOf(memoryValue));
                isNewNumber = true;
                break;
            case "MS":
                memoryValue = currentValue;
                isNewNumber = true;
                break;
            case "M+":
                memoryValue += currentValue;
                isNewNumber = true;
                break;
            case "M-":
                memoryValue -= currentValue;
                isNewNumber = true;
                break;
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%s", result);
        }
    }
}