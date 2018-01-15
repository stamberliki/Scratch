package com.mygdx.game;

/**
 * Created by Anony on 9/23/2017.
 */

public interface character_interface {
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
    void attack(String name);
    String findNearestEnemy();
    boolean enemy();
}
