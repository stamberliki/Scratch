package com.mygdx.game;

import com.mygdx.game.entity.gameException;

/**
 * Created by Anony on 9/23/2017.
 */

public interface character_interface {
    void moveUp() throws gameException;
    void moveDown() throws gameException;
    void moveRight() throws gameException;
    void moveLeft() throws gameException;
    void moveUp(int steps) throws gameException;
    void moveDown(int steps) throws gameException;
    void moveRight(int steps) throws gameException;
    void moveLeft(int steps) throws gameException;
    void attack(String name);
    String findNearestEnemy();
    boolean enemy();
    void end();
}
