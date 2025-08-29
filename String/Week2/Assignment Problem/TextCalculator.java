import java.util.*;

public class TextCalculator {
    static boolean validateExpression(String expr) {
        int balance = 0;
        char prev = ' ';
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (!(Character.isDigit(c) || "+-*/() ".indexOf(c) >= 0)) return false;
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
            if ("+-*/".indexOf(c) >= 0 && "+-*/".indexOf(prev) >= 0) return false;
            if (c != ' ') prev = c;
        }
        return balance == 0;
    }

    static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                int j = i;
                while (j < expr.length() && Character.isDigit(expr.charAt(j))) j++;
                tokens.add(expr.substring(i, j));
                i = j;
            } else if ("+-*/()".indexOf(c) >= 0) {
                tokens.add(Character.toString(c));
                i++;
            } else i++;
        }
        return tokens;
    }

    static int evaluateTokens(List<String> tokens, StringBuilder steps) {
        for (int i = 0; i < tokens.size(); ) {
            if (tokens.get(i).equals("*") || tokens.get(i).equals("/")) {
                int a = Integer.parseInt(tokens.get(i - 1));
                int b = Integer.parseInt(tokens.get(i + 1));
                int res = tokens.get(i).equals("*") ? a * b : a / b;
                tokens.set(i - 1, Integer.toString(res));
                tokens.remove(i);
                tokens.remove(i);
                steps.append(String.join(" ", tokens)).append("\n");
            } else i++;
        }
        for (int i = 0; i < tokens.size(); ) {
            if (tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
                int a = Integer.parseInt(tokens.get(i - 1));
                int b = Integer.parseInt(tokens.get(i + 1));
                int res = tokens.get(i).equals("+") ? a + b : a - b;
                tokens.set(i - 1, Integer.toString(res));
                tokens.remove(i);
                tokens.remove(i);
                steps.append(String.join(" ", tokens)).append("\n");
            } else i++;
        }
        return Integer.parseInt(tokens.get(0));
    }

    static int evaluate(String expr, StringBuilder steps) {
        while (expr.contains("(")) {
            int close = expr.indexOf(")");
            int open = expr.lastIndexOf("(", close);
            String sub = expr.substring(open + 1, close);
            List<String> tokens = tokenize(sub);
            int val = evaluateTokens(tokens, steps);
            expr = expr.substring(0, open) + val + expr.substring(close + 1);
        }
        List<String> tokens = tokenize(expr);
        return evaluateTokens(tokens, steps);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter expression (or 'exit'): ");
            String expr = sc.nextLine();
            if (expr.equalsIgnoreCase("exit")) break;
            if (!validateExpression(expr)) {
                System.out.println("Invalid expression");
                continue;
            }
            StringBuilder steps = new StringBuilder();
            System.out.println("Original: " + expr);
            int result = evaluate(expr, steps);
            System.out.println("Steps:\n" + steps);
            System.out.println("Final Result: " + result);
        }
    }
}
