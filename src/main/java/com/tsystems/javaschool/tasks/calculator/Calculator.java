package com.tsystems.javaschool.tasks.calculator;

import java.util.LinkedList;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        try {

            LinkedList<Double> num = new LinkedList<>();
            LinkedList<Character> operator = new LinkedList<>();

            for (int i = 0; i < statement.length(); i++) {
                char c = statement.charAt(i);
                if (isSpace(c))
                    continue;
                if (c == '(')
                    operator.add('(');
                else if (c == ')') {
                    while (operator.getLast() != '(')
                        processOperator(num, operator.removeLast());
                    operator.removeLast();
                } else if (isOperator(c)) {
                    while (!operator.isEmpty() && priority(operator.getLast()) >= priority(c))
                        processOperator(num, operator.removeLast());
                    operator.add(c);
                } else {
                    StringBuilder operand = new StringBuilder();
                    while (i < statement.length() && (Character.isDigit(statement.charAt(i)) || statement.charAt(i) == '.')) {
                        operand.append(statement.charAt(i++));
                    }
                    --i;
                    num.add(Double.parseDouble(operand.toString()));
                }
            }
            while (!operator.isEmpty())
                processOperator(num, operator.removeLast());
            String result = String.valueOf(num.get(0));
            if (result.split("\\.")[1].equals("0")) {
                result = result.split("\\.")[0];
            }
            return result;
        } catch (RuntimeException e) {
            return null;
        }
    }


    private boolean isSpace(char c) {
        return c == ' ';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    private int priority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    private void processOperator(LinkedList<Double> num, char operator) {
        double r = num.removeLast();
        double l = num.removeLast();
        switch (operator) {
            case '+':
                num.add(l + r);
                break;
            case '-':
                num.add(l - r);
                break;
            case '*':
                num.add(l * r);
                break;
            case '/':
                num.add(l / r);
                break;
            case '%':
                num.add(l % r);
                break;
        }
    }
}
