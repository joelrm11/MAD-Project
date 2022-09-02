package com.example.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private ArrayList<Bitmap> arrBms =new ArrayList<>();                        //creating array of bitmap to animate the bird
    private int count,vFlap,idCurrentBitmap;
    private float drop;

  /*  private int width;
    private int height;*/

    public Bird()
    {
        this.count=0;
        this.vFlap=5;
        this.idCurrentBitmap=0;
        this.drop=0;
    }
    public void draw(Canvas canvas)
    {
        drop();
        canvas.drawBitmap(this.getBm(),this.x,this.y,null);
    }

    private void drop() {
        this.drop+=0.6;
        this.y+=this.drop;
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;

        //we need to scale those Bitmaps to height and width of the bird

        for(int i=0;i<arrBms.size();i++)
        {
            this.arrBms.set(i,Bitmap.createScaledBitmap(this.arrBms.get(i),this.width, this.height,true));
        }
    }

    @Override
    public Bitmap getBm() {
        count++;
        if(this.count==this.vFlap)
        {
            for(int i=0;i<arrBms.size();i++)
            {
                if(i==arrBms.size()-1)
                {
                    this.idCurrentBitmap=0;
                    break;
                }
                else if(this.idCurrentBitmap == i)
                {
                    idCurrentBitmap=i+1;
                    break;
                }
            }
            count=0;
        }

        return this.arrBms.get(idCurrentBitmap);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}
