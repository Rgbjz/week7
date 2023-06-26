public class Application {
    public static double parse(String rpnString) {
        Deque<Double> stack = new LinkedList<>();

        String[] tokens = rpnString.split(" ");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new RPNParserException();
                }

                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = performOperation(token, operand1, operand2);
                stack.push(result);
            } else {
                throw new RPNParserException();
            }
        }

        if (stack.size() != 1) {
            throw new RPNParserException();
        }

        return stack.pop();
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static double performOperation(String operator, double operand1, double operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException();
                }
                return operand1 / operand2;
            default:
                throw new RPNParserException();
        }
    }

    public static void main(String[] args) {
        String rpnString = "10 20 + 30 40 + *";
        double result = parse(rpnString);
        System.out.println(result); // Output: 2100

        rpnString = "10 20 30.0 * +";
        result = parse(rpnString);
        System.out.println(result); // Output: 610

        rpnString = "10 20 30 () +";
        try {
            result = parse(rpnString);
        } catch (RPNParserException e) {
            System.out.println("RPNParserException"); // Output: RPNParserException
        }

        rpnString = "10 20 Е * +";
        try {
            result = parse(rpnString);
        } catch (RPNParserException e) {
            System.out.println("RPNParserException"); // Output: RPNParserException
        }

        rpnString = "0 0 /";
        try {
            result = parse(rpnString);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException"); // Output: ArithmeticException
        }
    }
        }
