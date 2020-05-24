package com.example.spaceavoider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Level1 extends AppCompatActivity implements View.OnTouchListener{

    Dialog dialog;

    public static boolean isLeftPressed = false; // нажата левая кнопка
    public static boolean isRightPressed = false; // нажата правая кнопка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        TextView text_money = findViewById(R.id.text_money);


        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);
        GameViewOne gameView1 = new GameViewOne(this); // создаём gameView
        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout); // находим gameLayout
        gameLayout.addView(gameView1); // и добавляем в него gameView
        Button leftButton = (Button) findViewById(R.id.leftButton); // находим кнопки
        Button rightButton = (Button) findViewById(R.id.rightButton);
        Button button_back = (Button)findViewById(R.id.button_back);

        leftButton.setOnTouchListener(this); // и добавляем этот класс как слушателя (при нажатии сработает onTouch)
        rightButton.setOnTouchListener(this);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView button_close = (TextView) dialog.findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){

                }
                dialog.dismiss();
            }
        });

        Button button_continue = (Button) dialog.findViewById(R.id.button_continue);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e){

                }
            }
        });
    }

    public boolean onTouch(View button, MotionEvent motion) {
        switch(button.getId()) { // определяем какая кнопка
            case R.id.leftButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        break;
                }
                break;
            case R.id.rightButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(Level1.this, GameLevels.class);
            startActivity(intent);
            finish();
        } catch (Exception e){

        }
    }
}