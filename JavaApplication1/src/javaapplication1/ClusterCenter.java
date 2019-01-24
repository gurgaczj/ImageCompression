/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author Kuba
 */
public class ClusterCenter {
    
    private int a;
    private int x;
    private int y;
    private int z;
    private int oldA;
    private int oldX;
    private int oldY;
    private int oldZ;

    public ClusterCenter(int a, int x, int y, int z) {
        this.a = a;
        this.x = x;
        this.y = y;
        this.z = z;
        this.oldA = 0;
        this.oldX = 0;
        this.oldY = 0;
        this.oldZ = 0;
    }
    
    public void setValues(int x, int y, int z){
        System.out.println("Stary klaster ("+getA()+", "+getX()+", "+getY()+", "+getZ()+")");
        setX(x);
        setY(y);
        setZ(z);
        System.out.println("Nowy klaster ("+getA()+", "+getX()+", "+getY()+", "+getZ()+")\n");
    }

    public int getOldA() {
        return oldA;
    }

    public void setOldA(int oldA) {
        
        this.oldA = oldA;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getOldZ() {
        return oldZ;
    }

    public void setOldZ(int oldZ) {
        this.oldZ = oldZ;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        setOldA(this.a);
        this.a = a;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        setOldX(this.x);
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        setOldY(this.y);
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        setOldZ(this.z);
        this.z = z;
    }
    
    
}
