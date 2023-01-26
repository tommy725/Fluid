package com.raycai.fluffie.util;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Arvin Jayanake on 08-09-2018
 */
public class CurrencyEditText extends TextInputEditText {

    private char mGroupDivider;
    private char mMonetaryDivider;
    private String mLocale = "";
    private boolean mShowSymbol;

    private char groupDivider;
    private char monetaryDivider;

    private Locale locale;
    private DecimalFormat numberFormat;

    private int fractionDigit;
    private String currencySymbol;

    private Double maxValue = null;
    private TextChangeListener textChangeListener;

    public void setTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        this.mLocale = "en_US";
        this.mShowSymbol = false;

        if (mLocale.equals("")) {
            locale = getDefaultLocale();
        } else {
            if (mLocale.contains("-"))
                mLocale = mLocale.replace("-", "_");

            String[] l = mLocale.split("_");
            if (l.length > 1) {
                locale = new Locale(l[0], l[1]);
            } else {
                locale = new Locale("", mLocale);
            }
        }

        initSettings();

        this.addTextChangedListener(onTextChangeListener);
    }

    /***
     * If user does not provide a valid locale it throws IllegalArgumentException.
     *
     * If throws an IllegalArgumentException the locale sets to default locale
     */
    private void initSettings() {
        boolean success = false;
        while (!success) {
            try {
                fractionDigit = Currency.getInstance(locale).getDefaultFractionDigits();

                DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
                if (mGroupDivider > 0)
                    symbols.setGroupingSeparator(mGroupDivider);
                groupDivider = symbols.getGroupingSeparator();

                if (mMonetaryDivider > 0)
                    symbols.setMonetaryDecimalSeparator(mMonetaryDivider);
                monetaryDivider = symbols.getMonetaryDecimalSeparator();

                currencySymbol = symbols.getCurrencySymbol();

                DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
                numberFormat = new DecimalFormat(df.toPattern(), symbols);

                success = true;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                locale = getDefaultLocale();
            }
        }
    }

    private Locale getDefaultLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return getContext().getResources().getConfiguration().getLocales().get(0);
        else
            return getContext().getResources().getConfiguration().locale;
    }

    /***
     *It resets text currently displayed If user changes separators or locale etc.
     */
    private void resetText() {
        String s = getText().toString();
        if (s.isEmpty()) {
            initSettings();
            return;
        }

        s = s.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                .replace(".", "").replace(" ", "")
                .replace(currencySymbol, "").trim();
        try {
            initSettings();
            s = format(s);
            removeTextChangedListener(onTextChangeListener);
            setText(s);
            setSelection(s.length());
            addTextChangedListener(onTextChangeListener);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String formatValue(double value){
        try {
            String mV = new DecimalFormat("0.00").format(value);
            mV = mV.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                    .replace(".", "").replace(" ", "")
                    .replace(currencySymbol, "").trim();
            mV = format(mV);
            return mV;
        }catch (Exception ex){
            return null;
        }
    }

    private TextWatcher onTextChangeListener = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0)
                return;

            removeTextChangedListener(this);

            /***
             * Clear input to get clean text before format
             * '\u0020' is empty character
             */
            String text = s.toString();
            text = text.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                    .replace(".", "").replace(" ", "")
                    .replace(currencySymbol, "").trim();
            try {
                text = format(text);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            setText(text);
            setSelection(text.length());

            if (maxValue != null){
                try {
                    double cV = getCurrencyDouble();
                    if (cV > maxValue){
                        String mV = new DecimalFormat("0.00").format(maxValue);
                        mV = mV.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                                .replace(".", "").replace(" ", "")
                                .replace(currencySymbol, "").trim();
                        mV = format(mV);
                        setText(mV);
                        setSelection(mV.length());
                    }
                }catch (Exception ex){ }
            }

            if (textChangeListener != null){
                textChangeListener.onTextChanged();
            }

            addTextChangedListener(this);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    private String format(String text) throws ParseException {
        if (mShowSymbol)
            return numberFormat.format(Double.parseDouble(text) / Math.pow(10, fractionDigit));
        else
            return numberFormat.format(Double.parseDouble(text) / Math.pow(10, fractionDigit)).replace(currencySymbol, "");
    }

    /***
     * returns the decimal separator for current locale
     * for example; input value 1,234.56
     *              returns ','
     *
     * @return decimal separator char
     */
    public char getGroupDivider() {
        return groupDivider;
    }

    /***
     * sets how to divide decimal value and fractions
     * for example; If you want formatting like this
     *              for input value 1,234.56
     *              set ','
     * @param groupDivider char
     */
    public void setGroupDivider(char groupDivider) {
        this.mGroupDivider = groupDivider;
        resetText();
    }

    /***
     * returns the monetary separator for current locale
     * for example; input value 1,234.56
     *              returns '.'
     *
     * @return monetary separator char
     */
    public char getMonetaryDivider() {
        return monetaryDivider;
    }

    /***
     * sets how to divide decimal value and fractions
     * for example; If you want formatting like this
     *              for input value 1,234.56
     *              set '.'
     * @param monetaryDivider char
     */
    public void setMonetaryDivider(char monetaryDivider) {
        this.mMonetaryDivider = monetaryDivider;
        resetText();
    }

    /***
     *
     * @return current locale
     */
    public Locale getLocale() {
        return locale;
    }

    /***
     * Sets locale which desired currency format
     *
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        resetText();
    }

    /**
     * @return true if currency symbol of current locale is showing
     */
    public boolean showSymbol() {
        return this.mShowSymbol;
    }

    /***
     * Sets if currency symbol of current locale shows
     *
     * @param showSymbol
     */
    public void showSymbol(boolean showSymbol) {
        this.mShowSymbol = showSymbol;
        resetText();
    }

    /**
     * @return double value for current text
     */
    public double getCurrencyDouble() throws ParseException {
        String text = getText().toString();
        text = text.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                .replace(".", "").replace(" ", "")
                .replace(currencySymbol, "").trim();

        if (showSymbol())
            return Double.parseDouble(text.replace(currencySymbol, "")) / Math.pow(10, fractionDigit);
        else return Double.parseDouble(text) / Math.pow(10, fractionDigit);
    }

    /**
     * @return String value for current text
     */
    public String getCurrencyText() throws ParseException {
        if (showSymbol())
            return getText().toString().replace(currencySymbol, "");
        else return getText().toString();
    }


    public interface TextChangeListener{
        void onTextChanged();
    }
}
