/***
 * Suman Kumari
 * 27-09-20204
 * Java code to perform conversion and calculation
 */
package NumberConversionAndCalculation;
import java.util.Scanner;
//Method in this code is to act as the entry point for a Base Conversion and Arithmetic Operations program
public class BaseConversion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(Constant.BASE);
            System.out.println("Choose an option:");
            System.out.println("1. Convert between bases");
            System.out.println("2. Perform arithmetic operations");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            switch (choice) {
                case 1:
                    handleConversion(scanner);
                    break;
                case 2:
                    handleArithmeticOperations(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye! \n");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again. \n");
            }
        }
    }
   /* It allows the user to input a number in one base (e.g., binary, decimal,
    * hexadecimal, etc.) and convert it to another base. 
    * Input - 1010 2 10
    * Output - 10 */
    private static void handleConversion(Scanner scanner) {
        System.out.println("\nEnter the number, input base, and output base separated by spaces (e.g., 1010 2 10):");
        String input = scanner.nextLine();
        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            System.out.println("Invalid input format.\n");
            return;
        }

        String number = parts[0];
        int inputBase = Integer.parseInt(parts[1]);
        int outputBase = Integer.parseInt(parts[2]);

        if (inputBase < 2 || inputBase > 36 || outputBase < 2 || outputBase > 36) {
            System.out.println("Base must be between 2 and 36.\n");
            return;
        }

        // Convert input number from the input base to decimal
        double decimalValue = BaseConversionUtil.baseToDecimal(number, inputBase);
        if (decimalValue == -1) {
            System.out.println("Conversion failed.\n");
            return;
        }

        // Convert from decimal to the desired output base
        String convertedResult = BaseConversionUtil.decimalToBase(String.valueOf(decimalValue), outputBase);
        System.out.println("Converted number: " + convertedResult + "\n");
    }

    /* Handle arithmetic operations 
     * Input - add 1010 2 1011 2
     * Output - Result in decimal: 21.0
     * Choose output base: 16
     * Result in base 16: 15*/
    private static void handleArithmeticOperations(Scanner scanner) {
        System.out.println("\nEnter the operation (add, subtract, multiply, divide), two numbers, their respective bases separated by spaces.");
        System.out.println("For example: add 1010 2 1011 2");
        String input = scanner.nextLine();
        String[] parts = input.split("\\s+");

        if (parts.length != 5) {
            System.out.println("Invalid input format.\n");
            return;
        }

        String operation = parts[0];
        String num1 = parts[1];
        int base1 = Integer.parseInt(parts[2]);
        String num2 = parts[3];
        int base2 = Integer.parseInt(parts[4]);

        double decimalNum1 = BaseConversionUtil.baseToDecimal(num1, base1);
        double decimalNum2 = BaseConversionUtil.baseToDecimal(num2, base2);

        if (decimalNum1 == -1 || decimalNum2 == -1) {
            System.out.println("Conversion to decimal failed.\n");
            return;
        }

        double result = 0;

        // Perform the arithmetic operation
        switch (operation.toLowerCase()) {
            case "add":
                result = decimalNum1 + decimalNum2;
                break;
            case "subtract":
                result = decimalNum1 - decimalNum2;
                break;
            case "multiply":
                result = decimalNum1 * decimalNum2;
                break;
            case "divide":
                if (decimalNum2 == 0) {
                    System.out.println("Cannot divide by zero.\n");
                    return;
                }
                result = decimalNum1 / decimalNum2;
                break;
            default:
                System.out.println("Invalid operation.\n");
                return;
        }

        // Output the result
        System.out.println("Result in decimal: " + result);
        System.out.println("Choose output base:");
        int outputBase = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (outputBase < 2 || outputBase > 36) {
            System.out.println("Base must be between 2 and 36.\n");
            return;
        }

        String convertedResult = BaseConversionUtil.decimalToBase(String.valueOf(result), outputBase);
        System.out.println("Result in base " + outputBase + ": " + convertedResult + "\n");
    }
}

/* Utility class for base conversions
 *  Input - number = "101.101", base = 2
 *  Output - 5.625 */
class BaseConversionUtil {
    // Convert any base number to decimal
    public static double baseToDecimal(String number, int base) {
        String[] numberParts = number.split("\\.");
        double decimalResult = 0.0;
        if (!validateFormat(numberParts[0], base)) {
            System.out.println("Invalid format for base " + base + "\n");
            return -1;
        }
        if (numberParts.length == 2 && !validateFormat(numberParts[1], base)) {
            System.out.println("Invalid format for base " + base + "\n");
            return -1;
        }
        double integerPart = convertIntegerToDecimal(numberParts[0], base);
        double fractionalPart = 0.0;
        if (numberParts.length == 2) {
            fractionalPart = convertFractionToDecimal(numberParts[1], base);
        }
        return integerPart + fractionalPart;
    }

    /* Convert the integer part of a number to decimal
     * Input - number = "101", base = 2
     * Output - 5.0 */
    private static double convertIntegerToDecimal(String number, int base) {
        double decimalValue = 0.0;
        int length = number.length();
        long power = 1;

        for (int i = length - 1; i >= 0; i--) {
            char digit = number.charAt(i);
            long digitValue = (digit >= '0' && digit <= '9') ? digit - '0' : digit - 55;
            decimalValue += digitValue * power;
            power *= base;
        }

        return decimalValue;
    }

    /* Convert the fractional part of a number to decimal 
     * Input - number = "101", base = 2
     * Output - 0.625 */
    private static double convertFractionToDecimal(String number, int base) {
        double decimalValue = 0.0;
        double power = base;  // base^-1

        for (int i = 0; i < number.length(); i++) {
            char digit = number.charAt(i);
            long digitValue = (digit >= '0' && digit <= '9') ? digit - '0' : digit - 55;
            decimalValue += digitValue / power;
            power *= base;
        }

        return decimalValue;
    }

    /* Convert a decimal number to any base 
     * Input - number = "15.625", base = 2
     * Output - 1111.101 */
    public static String decimalToBase(String number, int base) {
        String[] numberParts = number.split("\\.");
        String result = convertIntegerFromDecimal(Long.parseLong(numberParts[0]), base);

        if (numberParts.length == 2) {
            result += "." + convertFractionFromDecimal(numberParts[1], base);
        }

        return result;
    }

    /* Convert the integer part of a decimal number to any base
     * Input - number = 255, base = 16
     * Output - FF */
    private static String convertIntegerFromDecimal(long number, int base) {
        StringBuilder result = new StringBuilder();

        while (number > 0) {
            long remainder = number % base;
            char digit = (remainder > 9) ? (char) (remainder + 55) : (char) (remainder + '0');
            result.insert(0, digit);  // Prepend the digit
            number /= base;
        }

        return result.length() == 0 ? "0" : result.toString();
    }
    /* Convert the fractional part of a decimal number to any base 
     * Input - fraction = "625", base = 2
     * Output -  "101" for binary, "A" for hexadecimal.*/
    private static String convertFractionFromDecimal(String fraction, int base) {
        StringBuilder result = new StringBuilder();
        double fractionalValue = Double.parseDouble("0." + fraction);

        for (int i = 0; i < 5; i++) {  // Limit to 5 digits after the decimal
            fractionalValue *= base;
            int digit = (int) fractionalValue;
            result.append((digit > 9) ? (char) (digit + 55) : digit);
            fractionalValue -= digit;

            if (fractionalValue == 0) {
                break;
            }
        }

        return result.toString();
    }

    // Validate the number format for the given base
    private static boolean validateFormat(String number, int base) {
        String regex = (base <= 10) ? "[0-" + (base - 1) + "]+" : "[0-9A-" + (char) ('A' + base - 11) + "]+";
        return number.matches(regex);
    }
    public static class Constant {
    	public static String BASE = "Base Converter and Calculator";
    }

}
