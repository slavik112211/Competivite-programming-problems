class StringReverser{

public StringBuilder reverse(StringBuilder input){
    if(input.length()<2) return input;
    char currentChar;
    int secondHalfElementIndex;
    int lastElementOfFirstHalf = (int) Math.floor(input.length()/2);
    for(int i=0; i<lastElementOfFirstHalf; i++){
        currentChar = input.charAt(i);
        secondHalfElementIndex = input.length()-i-1;
        input.setCharAt(i, input.charAt(secondHalfElementIndex));
        input.setCharAt(secondHalfElementIndex, currentChar);
    }
    return input;
}

public static void main(String[] args){
    StringReverser sr = new StringReverser();
    StringBuilder reversedString = sr.reverse(new StringBuilder("asdfghjkl"));
    System.out.println("Correct answer: lkjhgfdsa; Answer: " + reversedString);

    reversedString = sr.reverse(new StringBuilder("qwertyuiop"));
    System.out.println("Correct answer: poiuytrewq; Answer: " + reversedString);
}
}