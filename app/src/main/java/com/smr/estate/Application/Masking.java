package com.smr.estate.Application;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Locale;

public class Masking implements TextWatcher {
    private static final int MAX_LENGTH = 12;
    private static final int MIN_LENGTH = 3;

    private String updatedText;
    private boolean editing;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (text.toString().equals(updatedText) || editing) return;

        String digits = text.toString().replaceAll("\\D", "");
        int length = digits.length();

        if (length <= MIN_LENGTH) {
            updatedText = digits;
            return;
        }

        if (length > MAX_LENGTH) {
            digits = digits.substring(0, MAX_LENGTH);
        }

        if (length <= 4) {
            String firstPart = digits.substring(0, 1);
            String secondPart = digits.substring(1);

            updatedText = String.format(Locale.US, "%s,%s", firstPart, secondPart);
        }
        if (length == 5) {
            String firstPart = digits.substring(0, 2);
            String secondPart = digits.substring(2);

            updatedText = String.format(Locale.US, "%s,%s", firstPart, secondPart);
        }
        if (length == 6) {
            String firstPart = digits.substring(0, 3);
            String secondPart = digits.substring(3);

            updatedText = String.format(Locale.US, "%s,%s", firstPart, secondPart);
        }
        if (length == 7) {
            String firstPart = digits.substring(0, 1);
            String secondPart = digits.substring(1,4);
            String thirdPart = digits.substring(4);

            updatedText = String.format(Locale.US, "%s,%s,%s", firstPart, secondPart,thirdPart);
        }
        if (length == 8) {
            String firstPart = digits.substring(0, 2);
            String secondPart = digits.substring(2,5);
            String thirdPart = digits.substring(5);

            updatedText = String.format(Locale.US, "%s,%s,%s", firstPart, secondPart,thirdPart);
        }
        if (length == 9) {
            String firstPart = digits.substring(0, 3);
            String secondPart = digits.substring(3,6);
            String thirdPart = digits.substring(6);

            updatedText = String.format(Locale.US, "%s,%s,%s", firstPart, secondPart,thirdPart);
        }
        if (length == 10) {
            String firstPart = digits.substring(0, 1);
            String secondPart = digits.substring(1,4);
            String thirdPart = digits.substring(4,7);
            String forthPart = digits.substring(7);

            updatedText = String.format(Locale.US, "%s,%s,%s,%s", firstPart, secondPart,thirdPart,forthPart);
        }
        if (length == 11) {
            String firstPart = digits.substring(0, 2);
            String secondPart = digits.substring(2,5);
            String thirdPart = digits.substring(5,8);
            String forthPart = digits.substring(8);

            updatedText = String.format(Locale.US, "%s,%s,%s,%s", firstPart, secondPart,thirdPart,forthPart);
        }
        if (length == 12) {
            String firstPart = digits.substring(0, 3);
            String secondPart = digits.substring(3,6);
            String thirdPart = digits.substring(6,9);
            String forthPart = digits.substring(9);

            updatedText = String.format(Locale.US, "%s,%s,%s,%s", firstPart, secondPart,thirdPart,forthPart);
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editing) return;

        editing = true;

        editable.clear();
        editable.insert(0, updatedText);

        editing = false;
    }
}
