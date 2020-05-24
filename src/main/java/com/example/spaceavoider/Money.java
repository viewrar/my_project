package com.example.spaceavoider;

import android.content.Context;

import java.util.Random;

public class Money extends SpaceBody{
    private float radius = (float) 1; // радиус
    private float minSpeed = (float) 0.3; // минимальная скорость
    private float maxSpeed = (float) 0.5; // максимальная скорость
    public int moneysCount = 0;

    public Money(Context context) {
        Random random = new Random();

        bitmapId = R.drawable.money;
        y=0;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

        init(context);
    }

    @Override
    public void update() {
        y += speed;
    }

    public boolean isCollision(float shipX, float shipY, float shipSize) {
        return !(((x+size) < shipX)||(x > (shipX+shipSize))||((y+size) < shipY)||(y > (shipY+shipSize)));
    }

    public int checkMoney(){
        return moneysCount;
    }
}
