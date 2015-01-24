/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orth.frc.fieldpositioning;

/**
 *
 * @author Max
 */
public class MovableFieldObject {
    
    public MovableFieldObject(double x, double y, double r) {
        collisionRadius = r;
        this.x = x;
        this.y = y;
    }
    
    public double collisionRadius;
    /**
     * Exact x and y on the field.
     */
    public double x, y;
    
    public boolean isCollision(MovableFieldObject a, MovableFieldObject b) {
        return (a.collisionRadius + b.collisionRadius)*(a.collisionRadius + b.collisionRadius) > ( (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }
    
    
    
}
