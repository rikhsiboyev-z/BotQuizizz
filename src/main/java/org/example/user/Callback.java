package org.example.user;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Callback {

    CREATE_QUIZ("1"), MY_QUIZ("2");

    @Getter
    private final String  value;




}
