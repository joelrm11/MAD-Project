package com.example.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bird bird;
    private final Handler handler;
    private Runnable r;
    private ArrayList<Pipe> arrPipes;
    private int sumpipe,distance;
    private int score,bestscore;
    private boolean start;



    //create a bird on GameView
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        score=0;
        bestscore=0;
        start=false;

        initBird();
        initPipe();

        handler=new Handler();

        r=new Runnable() {          //create a loop to update interface
            @Override
            public void run() {
                invalidate();       //to update draw method
            }
        };

    }

    private void initPipe() {
        sumpipe=6;
        distance = 400*Constants.SCREEN_HEIGHT/1920;
        arrPipes = new ArrayList<>();
        for(int i=0;i<sumpipe;i++)

        {
            if(i<sumpipe/2)
            {
                this.arrPipes.add(new Pipe(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200*Constants.SCREEN_WIDTH/1000)/(sumpipe/2)),
                       0,200*Constants.SCREEN_WIDTH/1080,Constants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe2));
                this.arrPipes.get(this.arrPipes.size()-1).randomY();

            }
            else
            {
                this.arrPipes.add(new Pipe(this.arrPipes.get(i-sumpipe/2).getX(),this.arrPipes.get(i-sumpipe/2).getY()
                +this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance,200*Constants.SCREEN_WIDTH/1080,Constants.SCREEN_HEIGHT/1920));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe1));
            }
        }
    }

    private void initBird() {
        bird=new Bird();
        bird.setHeight(100*Constants.SCREEN_HEIGHT/1920);         // seting width and height of bird
        bird.setWidth(100*Constants.SCREEN_WIDTH/1000);


        bird.setX(100*Constants.SCREEN_WIDTH/1080);                //seting display coordinates
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);


        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));
        bird.setArrBms(arrBms);
    }


    public void draw(Canvas canvas)  //canvas is for drawing
    {
        super.draw(canvas);
        if (start)
        {
            bird.draw(canvas);  //call of bird method to render it on GameView
            for(int i=0;i<sumpipe;i++)
            {
                if(bird.getRec().intersect(arrPipes.get(i).getRec()) || bird.getY()-bird.getHeight()<0|| bird.getY()>Constants.SCREEN_HEIGHT)
                {
                    Pipe.speed=0;
                    MainActivity.txt_score_over.setText(MainActivity.txt_score.getText());
                    MainActivity.txt_best_score.setText(""+bestscore);
                    MainActivity.txt_score.setVisibility(INVISIBLE);
                    MainActivity.r1_game_over.setVisibility(VISIBLE);
                }
                if(this.bird.getX()+this.bird.getWidth()>arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2
                        && this.bird.getX()+this.bird.getWidth()<=arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2+Pipe.speed
                        && i<sumpipe/2){
                    score++;
                    MainActivity.txt_score.setText(""+score);
                }
                if(this.arrPipes.get(i).getX()<-arrPipes.get(i).getWidth())
                {
                    this.arrPipes.get(i).setX(Constants.SCREEN_WIDTH);
                    if(i < sumpipe/2)
                    {
                        arrPipes.get(i).randomY();
                    }
                    else
                    {
                        arrPipes.get(i).setY(this.arrPipes.get(i-sumpipe/2).getY()
                                +this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance);
                    }
                }
                this.arrPipes.get(i).draw(canvas);
            }
        }
        else
        {
            if(bird.getY()>Constants.SCREEN_HEIGHT/2)
            {
                bird.setDrop(-15*Constants.SCREEN_HEIGHT/1920);
            }
            bird.draw(canvas);
        }
        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_DOWN)
        {
            bird.setDrop(-15);
        }
        return true;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
        MainActivity.txt_score.setText("0");
        score=0;
        initPipe();
        initBird();
    }
}
