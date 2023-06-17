/*
 *
 * Copyright (C) 2023 Zokirjon Mamadjonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zam.mathforkids.model;

import com.zam.mathforkids.utils.AppConstants;

public class Question {

    private String operation;
    private int max, min, a, b, c;
    private int[] random = new int[4];

    public Question(String operation, int max, int min) {
        this.operation = operation;
        this.max = max;
        this.min = min;

        a = getRandomNumber(false);

        setupSwitch();

        random[0] = getRandomNumber(true);
        random[1] = getRandomNumber(true);
        random[2] = getRandomNumber(true);
        random[3] = getRandomNumber(true);
    }

    private int getRandomNumber(boolean check) {
        if (check) {
            int x = 1, r = (int) (Math.random() * ((c + (c / 2) - c / 2) + 1)) + c / 2;

            while (x != 0) {
                if (r == c) {
                    r = (int) (Math.random() * ((c + (c / 2) - c / 2) + 1)) + c / 2;
                }
                else {
                    x = 0;
                }
            }
            return r;
        }
        else {
            return (int) (Math.random() * ((max - min) + 1)) + min;
        }
    }

    private void setupSwitch() {
        switch (operation) {
            case AppConstants.ADDITION:
                b = getRandomNumber(false);
                c = a + b;
                break;
            case AppConstants.SUBTRACTION:
                b = getRandomNumber(a - 1);
                c = a - b;
                break;
            case AppConstants.MULTIPLICATION:
                b = getRandomNumber(min / 5);
                c = a * b;
                break;
            case AppConstants.DIVISION:
                b = getRandomNumber(a);
                while (a % b != 0) {
                    b = getRandomNumber(a);
                }
                c = a / b;
                break;
        }
    }

    private int getRandomNumber(int mx) {
        return (int) (Math.random() * (mx - 1) + 1) + 1;
    }

    public String getQuestion() {
        return a + operation + b + AppConstants.EQUAL + c;
    }

    public String getA() {
        return String.valueOf(a);
    }

    public String getB() {
        return  String.valueOf(b);
    }

    public int getC() {
        return  c;
    }

    public int[] getRandom() {
        return random;
    }
}

