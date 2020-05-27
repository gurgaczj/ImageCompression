/*
 * This file is part of ImageCompression.

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package imagecompression;

/**
 *
 * @author Jakub Gurgacz
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
