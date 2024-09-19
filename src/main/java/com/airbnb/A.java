package com.airbnb;



public class A {
    //Integer array , random numbers,3rd largest
    public static void main(String[] args) {
       String s="backend developer";
        String caps = capsWord(s);
        System.out.println(caps);

    }
     public static  String  capsWord(String str){
         String[] split = str.split("\\s+");
         StringBuilder caps = new StringBuilder();
         for(String word:split){
             if(!word.isEmpty()){
                 caps.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1))
                         .append(" ");
             }
         }
         return caps.toString().trim();

     }
}
