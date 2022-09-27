import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

public class Main{
    public static void main(String[] args) throws NumberFormatException {
        String[] signs = {"+", "-", "*", "/"};
        String[] regexSigns = {"\\+", "-", "\\*", "/"};
        Scanner scan = new Scanner(System.in);
        Converter converter = new Converter();
        try {
        String input = scan.nextLine();
        int signIndex = -1;
        for (int i = 0; i < signs.length; i++) {
            if (input.contains(signs[i])) {
                signIndex = i;
                break;
            }
        }
        if (signIndex == -1) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Строка не является математической операцией");
            }
            return;
        }
        String[] data = input.split(regexSigns[signIndex]);
        if (data.length > 2) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Формат математической операции не удовлетворяет заданию");
            }
        } else {
            if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
                int a, b;
                boolean isRoman = converter.isRoman(data[0]);
                if (isRoman) {
                    a = converter.romanToArab(data[0]);
                    b = converter.romanToArab(data[1]);
                } else {
                    a = Integer.parseInt(data[0]);
                    b = Integer.parseInt(data[1]);
                }
                if (a < 1 || a > 10 || b < 1 || b > 10) {
                    try {
                        throw new IOException();
                    } catch (IOException e) {
                        System.out.println("Введённые числа не удовлетворяют заданию");
                        return;
                    }
                }
                int output = switch (signs[signIndex]) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "/" -> a / b;
                    default -> a * b;
                };
                if (isRoman) {
                    if (output < 1) {
                        try {
                            throw new Exception();
                        } catch (Exception e) {
                            System.out.println("В римской системе нет отрицательных чисел");
                            return;
                        }
                    }
                    System.out.println(converter.arabToRim(output));
                } else {
                    System.out.println(output);
                }
            } else {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Используются одновременно разные системы счисления");
                }
            }
        }
        }catch (NumberFormatException e){
            System.out.println("Формат математической операции не удовлетворяет заданию");
        }
    }
    static class Converter{
        TreeMap<Character, Integer> rimToArabKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabToRimKeyMap = new TreeMap<>();
        Converter(){
            rimToArabKeyMap.put('I', 1);
            rimToArabKeyMap.put('V', 5);
            rimToArabKeyMap.put('X', 10);

            arabToRimKeyMap.put(100, "C");
            arabToRimKeyMap.put(90, "XC");
            arabToRimKeyMap.put(50, "L");
            arabToRimKeyMap.put(40, "XL");
            arabToRimKeyMap.put(10, "X");
            arabToRimKeyMap.put(9, "IX");
            arabToRimKeyMap.put(5, "V");
            arabToRimKeyMap.put(4, "IV");
            arabToRimKeyMap.put(1, "I");
        }
        boolean isRoman(String number){
            return rimToArabKeyMap.containsKey(number.charAt(0));
        }
        String arabToRim(int number){
            StringBuilder roman = new StringBuilder();
            int arabToRimKey;
            do{
                arabToRimKey = arabToRimKeyMap.floorKey(number);
                roman.append(arabToRimKeyMap.get(arabToRimKey));
                number -= arabToRimKey;
            }while (number !=0);
            return roman.toString();
        }
        int romanToArab(String s){
            int indexOfLastSymbol = s.length()-1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = rimToArabKeyMap.get(arr[indexOfLastSymbol]);
            for (int i = indexOfLastSymbol-1; i>=0; i--){
                arabian = rimToArabKeyMap.get(arr[i]);
                if (arabian < rimToArabKeyMap.get(arr[i+1])){
                    result -= arabian;
                }else{
                    result += arabian;
                }
            }
            return result;
        }
    }
}