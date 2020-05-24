package com.example.spaceavoider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameViewFive extends SurfaceView implements Runnable {

    public static int maxX = 20; // размер по горизонтали
    public static int maxY = 28; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали

    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Ship ship;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<Asteroid> asteroids = new ArrayList<>(); // тут будут харанится астероиды
    private final int ASTEROID_INTERVAL = 50; // время через которое появляются астероиды (в итерациях)
    private int currentTime = 0;

    private ArrayList<Money> moneys = new ArrayList<>(); // тут будут харанится монеты
    private final int MONEY_INTERVAL = 300; // время через которое появляются монеты (в итерациях)
    private int currentTimeMoney = 0;
    public int moneysCount = 0;

    private void checkCollision(){ // перебираем все астероиды и проверяем не касается ли один из них корабля
        for (Asteroid asteroid : asteroids) {
            if(asteroid.isCollision(ship.x, ship.y, ship.size)){
                // игрок проиграл
                gameRunning = false; // останавливаем игру
                // TODO добавить анимацию взрыва
            }
        }
    }

    private void checkCollisionMoney(){ // перебираем все монеты и проверяем не касается ли одна из них корабля
        for (Money money : moneys) {
            if(money.isCollision(ship.x, ship.y, ship.size)){
                // игрок получил монету
                //TODO сделать, чтобы монета исчезла
                moneysCount++;
            }
        }
    }

    private void checkIfNewAsteroid(){ // каждые 50 итераций добавляем новый астероид
        if(currentTime >= ASTEROID_INTERVAL){
            Asteroid asteroid = new Asteroid(getContext());
            asteroids.add(asteroid);
            currentTime = 0;
        }else{
            currentTime ++;
        }
    }

    private void checkIfNewMoney(){ // каждые 300 итераций добавляем новый астероид
        if(currentTimeMoney >= MONEY_INTERVAL){
            Money money = new Money(getContext());
            moneys.add(money);
            currentTimeMoney = 0;
        }else{
            currentTimeMoney ++;
        }
    }

    @Override
    public void run() {
        while (gameRunning) {
            update();
            draw();
            checkCollision();
            checkCollisionMoney();
            checkIfNewMoney();
            checkIfNewAsteroid();
            control();
        }
    }

    private void update() {
        if(!firstTime) {
            ship.update();
            for (Asteroid asteroid : asteroids) {
                asteroid.update();
            }
            for (Money money : moneys) {
                money.update();
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                ship = new Ship(getContext()); // добавляем корабль
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawColor(Color.BLACK); // заполняем фон чёрным

            ship.drow(paint, canvas); // рисуем корабль

            for(Asteroid asteroid: asteroids){ // рисуем астероиды
                asteroid.drow(paint, canvas);
            }

            for (Money money : moneys) { // рисуем монеты
                money.drow(paint, canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }

    private void control() { // пауза на 17 миллисекунд
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public GameViewFive(Context context) {
        super(context);
        //инициализируем обьекты для рисования
        surfaceHolder = getHolder();
        paint = new Paint();

        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
    }
}
