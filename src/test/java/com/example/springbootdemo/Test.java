package com.example.springbootdemo;

public class Test {
    public static void main(String args[]) {
        String str1 = "-55555555";
        String str2 = "-666666666";
        System.out.println(bigAdd(str1, str2));
    }

    public static int[] add(int[] a, int[] b) {
        if (a.length < b.length) {
            return null;
        }
        int size = a.length;
        int t = 0;//进位
        for (int i = 0; i < size; i++) {
            int k = a[size - i - 1] + b[size - i - 1];
            k += t;//加进位
            if (k >= 10) {
                t = 1;
            } else {
                t = 0;
            }
            k %= 10;
            a[size - i - 1] = k;
        }

        return a;
    }

    public static String bigAdd(String a, String b) {
        if (a == null || b == null) {
            throw new NullPointerException();
        }
        boolean af = true, bf = true;
        if (a.charAt(0) == '-') {
            af = false;
            a = a.substring(1);
        }
        if (b.charAt(0) == '-') {
            bf = false;
            b = b.substring(1);
        }
        int maxsize = a.length() > b.length() ? a.length() + 2 : b.length() + 2;
        int[] m = new int[maxsize];
        int[] n = new int[maxsize];
        m[0] = af ? 0 : 9;
        n[0] = bf ? 0 : 9;
        for (int i = 0; i < a.length(); i++) {
            m[maxsize - a.length() + i] = Integer.parseInt("" + a.charAt(i));
        }
        for (int i = 0; i < b.length(); i++) {
            n[maxsize - b.length() + i] = Integer.parseInt("" + b.charAt(i));
        }
        int[] result = add(buma(m), buma(n));
        return intToString(buma(result));
    }

    public static String intToString(int[] a) {
        StringBuffer s = new StringBuffer();
        if (a[0] == 9) {
            s.append('-');
        } else {
            //s.append('0');
        }
        int tag = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] == 0) {
                tag++;
            } else {
                break;
            }
        }
        for (int j = tag + 1; j < a.length; j++) {
            s.append(a[j]);
        }
        if (s.length() == 0) {
            return "0";
        }
        return s.toString();
    }

    public static int[] buma(int[] a) {
        if (a[0] == 9) {
            for (int i = 1; i < a.length; i++) {
                a[i] = 9 - a[i];
            }
            int[] tmp = new int[a.length];
            tmp[a.length - 1] = 1;
            return add(a, tmp);
        } else {
            return a;
        }
    }
}