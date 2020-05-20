package com.example.countwidget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;

public class CountWidget extends RelativeLayout {
    private Context context;
    private AttributeSet attrs;
    private int styleAttr;
    private OnClickListener mListener;
    private double initialNumber;
    private double lastNumber;
    private double currentNumber;
    private double finalNumber;
    private TextView textView;
    private String m_Text = "";
    private OnValueChangeListener mOnValueChangeListener;

    public ImageButton addBtn, subtractBtn;

    public CountWidget(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CountWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public CountWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.styleAttr = defStyleAttr;
        initView();
    }

    private void initView() {
        inflate(context, R.layout.count_widget_layout, this);
        final int defaultColor = ContextCompat.getColor(context,R.color.colorPrimary);
        final int defaultTextColor = ContextCompat.getColor(context,R.color.colorText);
        final Drawable defaultDrawable = ContextCompat.getDrawable(context,R.drawable.background);
        final Drawable defaultLeftDrawable= ContextCompat.getDrawable(context,R.drawable.ic_subtract);
        final Drawable defaultRightDrawable= ContextCompat.getDrawable(context,R.drawable.ic_add);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CounterWidget,
                styleAttr, 0);

        initialNumber = a.getFloat(R.styleable.CounterWidget_initialNumber, 0.0f);
        finalNumber = a.getFloat(R.styleable.CounterWidget_finalNumber, Float.MAX_VALUE);
        float textSize = a.getDimension(R.styleable.CounterWidget_textSize, 20);
        int color = a.getColor(R.styleable.CounterWidget_backGroundColor, defaultColor);
        final int dialogButtonColor=a.getColor(R.styleable.CounterWidget_dialogButtonColor, defaultColor);
        int textColor = a.getColor(R.styleable.CounterWidget_textColor, defaultTextColor);
        Drawable leftDrawable = a.getDrawable(R.styleable.CounterWidget_leftDrawable);
        Drawable rightDrawable = a.getDrawable(R.styleable.CounterWidget_rightDrawable);
        Drawable drawable=a.getDrawable(R.styleable.CounterWidget_drawable);

        subtractBtn = findViewById(R.id.subtract);
        addBtn = findViewById(R.id.add);
        textView = findViewById(R.id.count);
        LinearLayout mLayout = findViewById(R.id.layout);


        textView.setTextColor(textColor);
        textView.setTextSize(textSize);

//        if (drawable == null) {
//            drawable = defaultDrawable;
//        }
//        assert drawable != null;
//        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
//        mLayout.setBackground(drawable);

        if (leftDrawable == null) {
            leftDrawable = defaultLeftDrawable;
        }
        subtractBtn.setImageDrawable(leftDrawable);

        if ( rightDrawable== null) {
            rightDrawable = defaultRightDrawable;
        }
        addBtn.setImageDrawable(rightDrawable);

        textView.setText(String.valueOf(initialNumber));

        currentNumber = initialNumber;
        lastNumber = initialNumber;

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                double num = Double.valueOf(textView.getText().toString());
                setNumber(String.valueOf(num - 1), true);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                double num = Double.valueOf(textView.getText().toString());
                setNumber(String.valueOf(num + 1), true);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(dialogButtonColor);
            }
        });
        a.recycle();
    }

    private void showAlertDialog(final int dialogButtonColor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
        View dialogView= LayoutInflater.from(context).inflate(R.layout.dialog_layout,null,false);
        final EditText input=dialogView.findViewById(R.id.input);
        input.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final AlertDialog dialog=builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                m_Text = input.getText().toString();
                double no=Double.parseDouble(m_Text);
                DecimalFormat df = new DecimalFormat("#.#");
                no= Double.valueOf(df.format(no));
                m_Text=String.valueOf(no);
                if(!m_Text.isEmpty())
                    setNumber(m_Text,true);
                dialog.dismiss();
            }
        }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(dialogButtonColor);
            }
        });
        builder.setView(dialogView);
        builder.show();
    }

    private void callListener(View view) {
        if (mListener != null) {
            mListener.onClick(view);
        }

        if (mOnValueChangeListener != null) {
            if (lastNumber != currentNumber) {
                mOnValueChangeListener.onValueChange(this, lastNumber, currentNumber);
            }
        }
    }

    public String getNumber() {
        return String.valueOf(currentNumber);
    }

    public void setNumber(String number) {
        lastNumber = currentNumber;
        this.currentNumber = Double.parseDouble(number);
        if (this.currentNumber > finalNumber) {
            this.currentNumber = finalNumber;
        }
        if (this.currentNumber < initialNumber) {
            this.currentNumber = initialNumber;
        }
        textView.setText(String.valueOf(currentNumber));
    }

    public void setNumber(String number, boolean notifyListener) {
        setNumber(number);
        if (notifyListener) {
            callListener(this);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        mOnValueChangeListener = onValueChangeListener;
    }

    @FunctionalInterface
    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnValueChangeListener {
        void onValueChange(CountWidget view, double oldValue, double newValue);
    }

    public void setRange(double startingNumber, double endingNumber) {
        this.initialNumber = startingNumber;
        this.finalNumber = endingNumber;
    }

    public void updateColors(int backgroundColor, int textColor) {
        this.textView.setBackgroundColor(backgroundColor);
        this.addBtn.setBackgroundColor(backgroundColor);
        this.subtractBtn.setBackgroundColor(backgroundColor);

        this.textView.setTextColor(textColor);
    }

    public void updateTextSize(int unit, float newSize) {
        this.textView.setTextSize(unit, newSize);
    }
}
