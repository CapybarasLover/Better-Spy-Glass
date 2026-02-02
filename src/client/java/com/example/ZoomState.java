package com.example;

public class ZoomState {
    private static double currZoom = 0.5;

    public static void adjust(double scrollDelta){
        double step = 0.3;
        currZoom += scrollDelta * step * currZoom;

        if(currZoom > 8.0){
            currZoom = 9.0;
        }
        if(currZoom < 0.5){
            currZoom = 0.5;
        }
    }

    public static double getCurrZoom(){
        return currZoom;
    }

    public static void reset(){
        currZoom = 1.0;
    }
}
