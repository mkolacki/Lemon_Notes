import com.sun.java_cup.internal.runtime.Scanner;
import com.sun.java_cup.internal.runtime.Symbol;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DoubleDV;
import com.sun.org.apache.xerces.internal.impl.dv.xs.IntegerDV;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.control.SortEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Scanner.*;

/**
 * Created by Michael K. on 4/3/2016.
 */
public class BasicCalculator extends Mode {

    public BasicCalculator() {
        super("Basic Calculator", "<calc>", "Numbers and basic operations", "Performs the input basic calculation when previewed or prompted", "<calc>5 + 3</calc>");
    }

    Boolean has_internal_operations(String being_evaluated){
        Boolean addition, subtraction, division, multiplication, integration, differentiation, exponentiation, log, natural_log;
        addition = being_evaluated.contains("+");
        subtraction = being_evaluated.contains("-");
        division = being_evaluated.contains("/");
        multiplication = being_evaluated.contains("*");
        integration = being_evaluated.trim().contains("int(");
        differentiation = being_evaluated.trim().contains("d/d");
        exponentiation = being_evaluated.contains("^");
        log = being_evaluated.contains("log");
        natural_log = being_evaluated.contains("ln");
        return addition || subtraction || division || multiplication || integration || differentiation || exponentiation || log || natural_log;
    }

    Boolean enough_paranthesis(String being_evaluated){
        String new_string = being_evaluated;
        int count1, count2, count3, count4;
        count1 = count2 = count3 = count4 = 0;
        while (new_string != null){
            if(new_string.startsWith("(")){
                count1++;
            }else if(new_string.startsWith(")")){
                count2++;
            }else if(new_string.startsWith("[")){
                count3++;
            }else if(new_string.startsWith("]")){
                count4++;
            }
            new_string = new_string.substring(1);
        }
        return (count1 == count2) && (count3 == count4);
    }

    String evalComponent(String being_evaluated){
        String bad_brackets, uh_oh;
        bad_brackets = "The number of parenthesis or brackets seems to be incorrect";
        uh_oh = "Something Happened";

        String evaluated = being_evaluated;
        if(has_internal_operations(being_evaluated)){
            if(enough_paranthesis(being_evaluated)){
                String  badly_formatted_exponentiation, badly_formatted_multiplication, badly_formatted_division, division_by_0, badly_formatted_addition, badly_formatted_subtraction;
                badly_formatted_exponentiation = "No number was provided after a ^. The expression could not be exponentiated.";
                badly_formatted_multiplication = "No number was provided after a *. The expression could not be multiplied.";
                badly_formatted_division = "No number was provided after a /. The expression could not be divided.";
                division_by_0 = "A division by 0 was attempted.";
                badly_formatted_addition = "No number was provided after a +. The expression could not be added.";
                badly_formatted_subtraction = "No number was provided after a -. The expression could not be subtracted.";

                if(being_evaluated.contains("(")){
                    //cases to handle outside: log and int and diff
                    String temp = being_evaluated.substring(being_evaluated.indexOf("("));
                    int count1, count2;
                    count1 = count2 = 0;
                    while(temp != null){
                        if(temp.startsWith("(")){
                            count1++;
                        }else if(temp.startsWith(")")){
                            count2++;
                        }
                        if(count1 == count2){
                            String reducing = evalComponent(evaluated.substring(evaluated.indexOf("("), evaluated.indexOf(temp)));
                            evaluated = evaluated.replace(evaluated.substring(evaluated.indexOf("("), being_evaluated.indexOf(temp)), reducing);
                        }
                        temp = temp.substring(1);
                    }
                    return evaluated;
                }
                if(being_evaluated.contains("[")){
                    String temp = being_evaluated.substring(being_evaluated.indexOf("["));
                    int count1, count2;
                    count1 = count2 = 0;
                    while(temp != null){
                        if(temp.startsWith("[")){
                            count1++;
                        }else if(temp.startsWith("]")){
                            count2++;
                        }
                        if(count1 == count2){
                            String reducing = evalComponent(evaluated.substring(evaluated.indexOf("["), evaluated.indexOf(temp)));
                            evaluated = evaluated.replace(evaluated.substring(evaluated.indexOf("]"), being_evaluated.indexOf(temp)), reducing);
                        }
                        temp = temp.substring(1);
                    }
                    return evaluated;
                }
                if(being_evaluated.contains("^")){
                    java.util.Scanner sc = new java.util.Scanner(being_evaluated);
                    String an_exponential = being_evaluated;
                    evaluated = being_evaluated;
                    //int next_int = 0;
                    double next_double = 0;
                    //float next_float = 0;
                    //long next_long = 0;
                    //boolean its_zero = false;
                    //int zero = 0;
                    while(sc.hasNext()) {
                        //Boolean hasInt, hasDouble, hasFloat, hasLong;
                        Boolean hasDouble = sc.hasNextDouble();
                        //hasInt = sc.hasNextInt();
                        //hasDouble = sc.hasNextDouble();
                        //hasFloat = sc.hasNextFloat();
                        //hasLong = sc.hasNextLong();
                        if (hasDouble) {
                            next_double = sc.nextDouble();
                            if (an_exponential.indexOf(Double.toString(next_double)) < an_exponential.indexOf("^")) {
                                if (Double.toString(next_double) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf(Double.toString(next_double)), being_evaluated.trim().indexOf("^")))) {
                                    //next number is right before the ^
                                    double exponentiate_this = next_double;
                                    while (!sc.hasNextDouble()) {
                                        sc.next();
                                    }
                                    double to_this_power = sc.nextDouble();
                                    if (Double.toString(to_this_power) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf("^"), being_evaluated.trim().indexOf(Double.toString(to_this_power))))) {
                                        double replacement;
                                        if (to_this_power < 0) {
                                            replacement = 1 / Math.pow(exponentiate_this, Math.abs(to_this_power));
                                        } else {
                                            replacement = Math.pow(exponentiate_this, to_this_power);
                                        }
                                        evaluated = evaluated.replace(an_exponential.substring(an_exponential.indexOf(Double.toString(exponentiate_this)), an_exponential.indexOf(Double.toString(to_this_power))), Double.toString(replacement));
                                        an_exponential = an_exponential.substring(an_exponential.indexOf(Double.toString(to_this_power)) + Double.toString(to_this_power).length());
                                    } else {
                                        //the number is after the ^, but not right after. This is badly formatted and all that will be returned is what was given.
                                        return evaluated;
                                    }

                                }//otherwise the next number is before the ^, but not RIGHT before it
                            }//otherwise the number is after the ^ in which case, it should either have been evaluated or it should remain as is
                        } else {
                            //this isn't a number. so... yeah. its covered in the setup of 'evaluated'
                            sc.next();
                        }
                    }
                    return evaluated;
                       /* if((next_int == 0) && hasInt){
                            next_int = sc.nextInt();
                            its_zero = next_int == 0;
                        }
                        if((next_double == 0) && hasDouble){
                            next_double = sc.nextDouble();
                            its_zero = next_double == 0;
                        }
                        if((next_float == 0) && hasFloat){
                            next_float = sc.nextFloat();
                            its_zero = next_float == 0;
                        }
                        if((next_long == 0) && hasLong){
                            next_long = sc.nextLong();
                            its_zero = next_long == 0;
                        }
                        if(hasInt){
                            if(being_evaluated.indexOf(Integer.toString(next_int)) < being_evaluated.indexOf("^")){
                                if (Integer.toString(next_int) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf(Integer.toString(next_int)), being_evaluated.trim().indexOf("^")))){
                                    //next_int is right before the ^
                                    int exponentiated = next_int;
                                    //now get whats after the ^
                                    hasInt = sc.hasNextInt();
                                    if(hasInt){
                                        next_int = sc.nextInt();
                                        its_zero = next_int == 0;
                                        Boolean int_comes_first = true;
                                        Boolean double_comes_first = false;
                                        Boolean float_comes_first = false;
                                        Boolean long_comes_first = false;
                                        if(hasDouble && (being_evaluated.indexOf(Double.toString(next_double)) < being_evaluated.indexOf("^"))){
                                            int_comes_first = being_evaluated.indexOf(Integer.toString(next_int)) < being_evaluated.indexOf(Double.toString(next_double));
                                            double_comes_first = !int_comes_first;
                                        }else {
                                            hasDouble = false;
                                            next_double = 0;
                                        }
                                        if(hasFloat && (being_evaluated.indexOf(Float.toString(next_float)) < being_evaluated.indexOf("^"))){
                                            int_comes_first = being_evaluated.indexOf(Integer.toString(next_int)) < being_evaluated.indexOf(Float.toString(next_float));
                                            float_comes_first = !int_comes_first;

                                        }else {
                                            hasFloat = false;
                                            next_float = 0;
                                        }
                                        if(hasLong){
                                            int_comes_first = being_evaluated.indexOf(Integer.toString(next_int)) < being_evaluated.indexOf(Long.toString(next_long));
                                            long_comes_first = !int_comes_first;
                                        }
                                        if(int_comes_first){

                                        }
                                        hasInt = false;
                                    }else

                                }else {
                                    //next_int is before the ^ but not RIGHT before it
                                }
                            }else {
                                //next int is after the ^. right after?
                            }
                        }*/

                    /*String temp = being_evaluated.substring(being_evaluated.indexOf("("));
                    int count1, count2;
                    count1 = count2 = 0;
                    while(temp != null){
                        if(temp.startsWith("(")){
                            count1++;
                        }else if(temp.startsWith(")")){
                            count2++;
                        }
                        if(count1 == count2){
                            String reducing = evalComponent(evaluated.substring(evaluated.indexOf("("), evaluated.indexOf(temp)));
                            evaluated = evaluated.replace(evaluated.substring(evaluated.indexOf("("), being_evaluated.indexOf(temp)), reducing);
                        }
                        temp = temp.substring(1);
                    }*/
                }else if(being_evaluated.contains("*")){
                    java.util.Scanner sc = new java.util.Scanner(being_evaluated);
                    String an_exponential = being_evaluated;
                    evaluated = being_evaluated;
                    double next_double = 0;
                    while(sc.hasNext()) {
                        Boolean hasDouble = sc.hasNextDouble();
                        if (hasDouble) {
                            next_double = sc.nextDouble();
                            if (an_exponential.indexOf(Double.toString(next_double)) < an_exponential.indexOf("*")) {
                                if (Double.toString(next_double) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf(Double.toString(next_double)), being_evaluated.trim().indexOf("*")))) {
                                    //next number is right before the *
                                    double multiply_this = next_double;
                                    while (!sc.hasNextDouble()) {
                                        sc.next();
                                    }
                                    double by_this = sc.nextDouble();
                                    if (Double.toString(by_this) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf("*"), being_evaluated.trim().indexOf(Double.toString(by_this))))) {
                                        double replacement = multiply_this * by_this;
                                        evaluated = evaluated.replace(an_exponential.substring(an_exponential.indexOf(Double.toString(multiply_this)), an_exponential.indexOf(Double.toString(by_this))), Double.toString(replacement));
                                        an_exponential = an_exponential.substring(an_exponential.indexOf(Double.toString(by_this)) + Double.toString(by_this).length());
                                    } else {
                                        //the number is after the *, but not right after. This is badly formatted and all that will be returned is what was given.
                                        return evaluated;
                                    }

                                }//otherwise the next number is before the *, but not RIGHT before it
                            }//otherwise the number is after the * in which case, it should either have been evaluated or it should remain as is
                        } else {
                            //this isn't a number. so... yeah. its covered in the setup of 'evaluated'
                            sc.next();
                        }
                    }
                    return evaluated;
                }else if(being_evaluated.contains("/")){
                    java.util.Scanner sc = new java.util.Scanner(being_evaluated);
                    String an_exponential = being_evaluated;
                    evaluated = being_evaluated;
                    double next_double = 0;
                    while(sc.hasNext()) {
                        Boolean hasDouble = sc.hasNextDouble();
                        if (hasDouble) {
                            next_double = sc.nextDouble();
                            if (an_exponential.indexOf(Double.toString(next_double)) < an_exponential.indexOf("/")) {
                                if (Double.toString(next_double) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf(Double.toString(next_double)), being_evaluated.trim().indexOf("/")))) {
                                    //next number is right before the /
                                    double divide_this = next_double;
                                    while (!sc.hasNextDouble()) {
                                        sc.next();
                                    }
                                    double by_this = sc.nextDouble();
                                    if(by_this == 0){
                                        //attempted division by 0
                                        return "Attempted Division By 0";
                                    }
                                    if (Double.toString(by_this) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf("/"), being_evaluated.trim().indexOf(Double.toString(by_this))))) {
                                        double replacement = divide_this * by_this;
                                        evaluated = evaluated.replace(an_exponential.substring(an_exponential.indexOf(Double.toString(divide_this)), an_exponential.indexOf(Double.toString(by_this))), Double.toString(replacement));
                                        an_exponential = an_exponential.substring(an_exponential.indexOf(Double.toString(by_this)) + Double.toString(by_this).length());
                                    } else {
                                        //the number is after the /, but not right after. This is badly formatted and all that will be returned is what was given.
                                        return evaluated;
                                    }

                                }//otherwise the next number is before the /, but not RIGHT before it
                            }//otherwise the number is after the / in which case, it should either have been evaluated or it should remain as is
                        } else {
                            //this isn't a number. so... yeah. its covered in the setup of 'evaluated'
                            sc.next();
                        }
                    }
                }else if(being_evaluated.contains("+")){
                    java.util.Scanner sc = new java.util.Scanner(being_evaluated);
                    String an_exponential = being_evaluated;
                    evaluated = being_evaluated;
                    double next_double = 0;
                    while(sc.hasNext()) {
                        Boolean hasDouble = sc.hasNextDouble();
                        if (hasDouble) {
                            next_double = sc.nextDouble();
                            if (an_exponential.indexOf(Double.toString(next_double)) < an_exponential.indexOf("+")) {
                                if (Double.toString(next_double) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf(Double.toString(next_double)), being_evaluated.trim().indexOf("+")))) {
                                    //next number is right before the +
                                    double add_this = next_double;
                                    while (!sc.hasNextDouble()) {
                                        sc.next();
                                    }
                                    double to_this = sc.nextDouble();
                                    if (Double.toString(to_this) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf("+"), being_evaluated.trim().indexOf(Double.toString(to_this))))) {
                                        double replacement = add_this + to_this;
                                        evaluated = evaluated.replace(an_exponential.substring(an_exponential.indexOf(Double.toString(add_this)), an_exponential.indexOf(Double.toString(to_this))), Double.toString(replacement));
                                        an_exponential = an_exponential.substring(an_exponential.indexOf(Double.toString(to_this)) + Double.toString(to_this).length());
                                    } else {
                                        //the number is after the +, but not right after. This is badly formatted and all that will be returned is what was given.
                                        return evaluated;
                                    }

                                }//otherwise the next number is before the +, but not RIGHT before it
                            }//otherwise the number is after the + in which case, it should either have been evaluated or it should remain as is
                        } else {
                            //this isn't a number. so... yeah. its covered in the setup of 'evaluated'
                            sc.next();
                        }
                    }
                }else if(being_evaluated.contains("-")){
                    java.util.Scanner sc = new java.util.Scanner(being_evaluated);
                    String an_exponential = being_evaluated;
                    evaluated = being_evaluated;
                    double next_double = 0;
                    while(sc.hasNext()) {
                        Boolean hasDouble = sc.hasNextDouble();
                        if (hasDouble) {
                            next_double = sc.nextDouble();
                            if (an_exponential.indexOf(Double.toString(next_double)) < an_exponential.indexOf("-")) {
                                if (Double.toString(next_double) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf(Double.toString(next_double)), being_evaluated.trim().indexOf("-")))) {
                                    //next number is right before the -
                                    double subtract_this = next_double;
                                    while (!sc.hasNextDouble()) {
                                        sc.next();
                                    }
                                    double by_this = sc.nextDouble();
                                    if (Double.toString(by_this) == (being_evaluated.trim().substring(being_evaluated.trim().indexOf("-"), being_evaluated.trim().indexOf(Double.toString(by_this))))) {
                                        double replacement = subtract_this - by_this;
                                        evaluated = evaluated.replace(an_exponential.substring(an_exponential.indexOf(Double.toString(subtract_this)), an_exponential.indexOf(Double.toString(by_this))), Double.toString(replacement));
                                        an_exponential = an_exponential.substring(an_exponential.indexOf(Double.toString(by_this)) + Double.toString(by_this).length());
                                    } else {
                                        //the number is after the -, but not right after. This is badly formatted and all that will be returned is what was given.
                                        return evaluated;
                                    }

                                }//otherwise the next number is before the -, but not RIGHT before it
                            }//otherwise the number is after the - in which case, it should either have been evaluated or it should remain as is
                        } else {
                            //this isn't a number. so... yeah. its covered in the setup of 'evaluated'
                            sc.next();
                        }
                    }
                }else {
                    return uh_oh;
                }
            }else {
                return bad_brackets;
            }
        }else {
            return evaluated;
        }
        return evaluated;
    }


    @Override
    public Text preview(Text textAreaBoxData) {
        String entry = textAreaBoxData.getText();
        Boolean addition, subtraction, division, multiplication, integration, differentiation, exponentiation, log, natural_log;
        addition = entry.contains("+");
        subtraction = entry.contains("-");
        division = entry.contains("/");
        multiplication = entry.contains("*");
        integration = entry.trim().contains("int(");
        differentiation = entry.trim().contains("d/d");
        exponentiation = entry.contains("^");
        log = entry.contains("log");
        natural_log = entry.contains("ln");
        if(addition || subtraction || division || multiplication || integration || differentiation || exponentiation || log || natural_log){
            java.util.Scanner line_scanner = new java.util.Scanner(entry);
            ArrayList<String> all_portions = new ArrayList<String>();
            if(entry.contains("(")){
                int count1, count2;
                count1 = count2 = 0;
                String new_string = entry;
                String portion;
                while (new_string != null){
                    if(new_string.startsWith("(")){
                        count1++;
                    }else if(new_string.startsWith(")")){
                        count2++;
                    }
                    new_string = new_string.substring(1);
                }
                if(count1 == count2){

                }
            }

        }else {
            System.out.println("No known operation");
        }


        return new Text("");
    }
}
