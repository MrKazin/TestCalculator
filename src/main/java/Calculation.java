import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculation {

    //данные для сравнения с вводом
    private List<String> operators = Arrays.asList(new String[]{"+","-","*","/"});
    private List<String> romanNumbers = Arrays.asList(new String[]{"I","II","III","IV","V","VI","VII","VIII","IX","X"});;
    private List<String> arabicNumbers = Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10"});;

    //чтение ввода
    public void readInput(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            specifyOperator(line);
        }
    }

    //определение наличия операторов
    private void specifyOperator(String line){
        int additionCountMatches = StringUtils.countMatches(line,"+");
        int substractionCountMatches = StringUtils.countMatches(line,"-");
        int multiplicationCountMatches = StringUtils.countMatches(line,"*");
        int divisionCountMatches = StringUtils.countMatches(line,"/");
        if(additionCountMatches == 1 && substractionCountMatches == 0 && multiplicationCountMatches == 0 && divisionCountMatches == 0){
            checkNumbersAndCalculate(line,"[+]");
        }
        else if(additionCountMatches == 0 && substractionCountMatches == 1 && multiplicationCountMatches == 0 && divisionCountMatches == 0){
            checkNumbersAndCalculate(line,"[-]");
        }
        else if(additionCountMatches == 0 && substractionCountMatches == 0 && multiplicationCountMatches == 1 && divisionCountMatches == 0){
            checkNumbersAndCalculate(line,"[*]");
        }
        else if(additionCountMatches == 0 && substractionCountMatches == 0 && multiplicationCountMatches == 0 && divisionCountMatches == 1){
            checkNumbersAndCalculate(line,"[/]");
        }
        else if(additionCountMatches == 0 && substractionCountMatches == 0 && multiplicationCountMatches == 0 && divisionCountMatches == 0){
            throw new IllegalArgumentException("строка не является математической операцией");
        }
        else{
            throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
    }


    //определение наличия операндов и определение системы счисления
    private void checkNumbersAndCalculate(String line, String regex){
        List<String> list = Arrays.asList(line.split(regex));
        String result;
        if (list.size() != 2){
            throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        else{
            if(arabicNumbers.contains(list.get(0)) && arabicNumbers.contains(list.get(1))){
                result = String.valueOf(calculate(arabicNumbers.indexOf(list.get(0))+1,arabicNumbers.indexOf(list.get(1))+1,regex));
                System.out.println(result);
            }
            else if(romanNumbers.contains(list.get(0)) && romanNumbers.contains(list.get(1))){
                result = transformToRoman(calculate(romanNumbers.indexOf(list.get(0))+1,romanNumbers.indexOf(list.get(1))+1,regex));
                System.out.println(result);
            }
            else{
                throw new IllegalArgumentException("используются одновременно разные системы счисления");
            }
        }

    }


    //считывание результата операции
    private int calculate(Integer firstValue, Integer secondValue, String regex){
        switch (regex){
            case "[+]":
                return firstValue+secondValue;
            case "[-]":
                return firstValue-secondValue;
            case "[*]":
                return firstValue*secondValue;
            case "[/]":
                return firstValue/secondValue;
            default:
                throw new IllegalArgumentException("ошибка чтения оператора");
        }
    }


    //перевод ответа из арабской системы в римскую
    private String transformToRoman(int arabicNumber){
        if((arabicNumber <= 0) || (arabicNumber > 4000)){
            throw new IllegalArgumentException("невозможно перевести результат");
        }
        List<RomanNumeral> list = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder builder = new StringBuilder();

        while ((arabicNumber > 0) && (i < list.size())){
            RomanNumeral item = list.get(i);
            if(item.getValue() <= arabicNumber){
                builder.append(item.name());
                arabicNumber -= item.getValue();
            } else{
                i++;
            }
        }
        return builder.toString();
    }


}
