package GeneralSubjects.Collections.LinkedList;

import java.util.LinkedList;

public class FindPar {
    public static boolean parOrder(String line){
        LinkedList<Character> stack = new LinkedList<>();
        for(Character c: line.toCharArray()){
            if(c == '('){
                stack.add('(');
            }
            if(c == '['){
                stack.add('[');
            }
            if(c == '{'){
                stack.add('{');
            }
            if(c == ')'){
                if(stack.isEmpty() || stack.pollLast()!='('){
                    return false;
                }
            }
            if(c == ']'){
                if(stack.isEmpty() || stack.pollLast()!='['){
                    return false;
                }
            }
            if(c == '}'){
                if(stack.isEmpty() || stack.pollLast()!='{'){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
