package com.main.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ScreenOverlay {

//    private void fadeOutStep(float delta){
//        if (fadeOut){
//            if (fadeTime == 0) fadeTime = minShade;
//            if (fadeTime <= 1) {
//                fadeTime += delta;
//                drawShadeOverlay(fadeTime);
//            }
//            else{
//                if (resetPos) {
//                    player.setPos( 1389, 635);
//                    player.setDirection('D');
//                }
//                fadeTime = 0;
//                fadeOut = false;
//                lockTime = false;
//                lockMovement = false;
//                lockPopup = false;
//                resetPos = false;
//            }
//        }
//    }
//    private void executeFadeOut(boolean resetPos){
//        if (fadeOut) return;
//        fadeOut = true;
//        lockMovement = true;
//        lockTime = true;
//        lockPopup = true;
//        showMenu = false;
//        this.resetPos = resetPos;
//        minShade = timeElapsed/secondsPerGameHour > 11 ? (timeElapsed - 11 * secondsPerGameHour)/(gameDayLengthInSeconds - 11 * secondsPerGameHour) : 0;
//    }
//    private void drawShadeOverlay(float alpha){
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(0, 0, 0, alpha); // Adjust alpha for darkness
//        shapeRenderer.rect(0, 0, gameMap.getWidth(), gameMap.getHeight());
//        shapeRenderer.end();
//        Gdx.gl.glDisable(GL20.GL_BLEND);
//        }




}
