package com.mypc.slotmachine.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mypc.slotmachine.R;
import com.mypc.slotmachine.adapter.SlotMachineAdapter;
import com.mypc.slotmachine.dialog.WinDialog;
import com.mypc.slotmachine.services.SingleToast;
import com.mypc.slotmachine.singleton.Win;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;


public class MainActivity extends AppCompatActivity {

    private TextView tvTextJackpot;
    private TextView tvCoins;
    private TextView tvLine;
    private TextView tvBet;
    private ImageButton ivBtnMinus;
    private ImageButton ivBtnPlus;
    private ImageButton btnSpin;
    private int counter = 5;
    private int coins = 995;
    private int line = 1;
    private int jackpot= 100000;

    private int rombCoin = 10;
    private int trefCoin = 15;
    private int chervCoin = 25;
    private int pickCoin = 35;
    private int meshokCoin = 50;
    private int kleverCoin = 70;

    private int podkova1Coin = 25;
    private int podkova2Coin = 50;
    private int podkova3Coin = 100;

    private int romb = 0;
    private int tref = 1;
    private int cherv = 2;
    private int pick = 3;
    private int meshok = 4;
    private int klever = 5;
    private int podkova = 6;
    private int myCoin;

    private boolean test = false;
    private boolean wheelScrolled = false;
    private WinDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextJackpot = findViewById(R.id.tvTextJackpot);
        tvCoins = findViewById(R.id.tvCoins);
        tvLine = findViewById(R.id.tvLine);
        ivBtnMinus = findViewById(R.id.ivBtnMinus);
        ivBtnPlus = findViewById(R.id.ivBtnPlus);
        btnSpin = findViewById(R.id.btnSpin);
        tvBet = findViewById(R.id.tvBet);

        initWheel(R.id.slot_1);
        initWheel(R.id.slot_2);
        initWheel(R.id.slot_3);

        ivBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>5){
                    counter = counter - 5;
                    coins = coins + 5;
                    line--;
                    tvBet.setText(String.valueOf(counter));
                    tvCoins.setText(String.valueOf(coins));
                }else {
                    SingleToast.show(getApplicationContext(), "Баланс не может быть отрицательным...", Toast.LENGTH_SHORT);
                }
            }
        });

        ivBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coins > 5) {
                    counter  = counter + 5;
                    coins  = coins -5;
                    line++;
                    tvBet.setText(String.valueOf(counter));
                    tvCoins.setText(String.valueOf(coins));
                } else {
                    SingleToast.show(getApplicationContext(), "Упс... Деньги закончились...", Toast.LENGTH_SHORT);
                }
            }
        });



        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test = true;
                btnSpin.setEnabled(false);
                int bet = Integer.parseInt(tvBet.getText().toString());
                myCoin = coins - 5;
                tvCoins.setText(String.valueOf(myCoin));

                tvTextJackpot.setText(String.valueOf(jackpot + bet));

                mixWheel(R.id.slot_1);
                mixWheel(R.id.slot_2);
                mixWheel(R.id.slot_3);
                btnSpin.setEnabled(true);
            }
        });

        if (test) {
            updateStatus();
        }

    }

    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }
        @Override
        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            updateStatus();
        }
    };

    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                updateStatus();
            }
        }
    };

    private void updateStatus() {
        Log.d("sadsadasdasdasd", "test");
        if (test() > 0) {
            coins = coins + test();

            Log.d("sadsadasdasdasd", String.valueOf(coins));
            Log.d("sadsadasdasdasd", String.valueOf(coins + test()));

            tvCoins.setText(String.valueOf(coins));
            Win.getInstance().setCounter(test());
            if (coins > 0) {
                myDialog();
            }
        }
    }

    private void initWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new SlotMachineAdapter(this));
        wheel.setCurrentItem((int)(Math.random() * 10));

        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(false);
    }

    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }

    private int test() {
        int value = getWheel(R.id.slot_1).getCurrentItem();
        int value1 = getWheel(R.id.slot_2).getCurrentItem();
        int value2 = getWheel(R.id.slot_3).getCurrentItem();
        if (podkova == value || podkova == value1 || podkova == value2){
            return (podkova1Coin*line);
        }else if (romb == value && romb == value1 && romb == value2){
            return rombCoin;
        }
        else if (tref == value && tref == value1 && tref == value2){
            return trefCoin;
        }
        else if (cherv == value && cherv == value1 && cherv == value2){
            return chervCoin;
        }
        else if (pick == value && pick == value1 && pick == value2){
            return pickCoin;
        }
        else if (meshok == value && meshok == value1 && meshok == value2){
            return meshokCoin;
        }
        else if (klever == value && klever == value1 && klever == value2){
            return kleverCoin;
        }
        else return 0;
    }

    private void mixWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.scroll(-350 + (int)(Math.random() * 50), 2000);
    }

    private void myDialog() {
        dialog = new WinDialog();
        dialog.setCancelable(true);
        dialog.show(getSupportFragmentManager(), "fragment");
    }
}

