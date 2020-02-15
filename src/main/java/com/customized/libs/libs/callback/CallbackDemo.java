package com.customized.libs.libs.callback;

public class CallbackDemo {

    public static void main(String[] args) {
        new CallbackDemo().handler(System.out::println);
    }

    private void handler(ICallback callback) {
        System.out.println("Do you know it's true?");
        callback.onResult("Yes I'do");
    }
}
