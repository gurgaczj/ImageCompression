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
public class Pixel {
    
    private int alpha;
    private int red;
    private int green;
    private int blue;
    private ClusterCenter cluster;
    private ClusterCenter oldCluster;
    private int x;
    private int y;

    public Pixel(int alpha, int red, int green, int blue, int x, int y) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.cluster = null;
        this.x = x;
        this.y = y;
        this.oldCluster = null;
    }
    
    public void setValuesAsCluster(){
        try{
        setAlpha(cluster.getA());
        setRed(cluster.getX());
        setGreen(cluster.getY());
        setBlue(cluster.getZ());
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }

    public ClusterCenter getOldCluster() {
        return oldCluster;
    }

    public void setOldCluster(ClusterCenter oldCluster) {
        this.oldCluster = oldCluster;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public ClusterCenter getCluster() {
        return cluster;
    }

    public void setCluster(ClusterCenter cluster) {
        setOldCluster(this.cluster);
        this.cluster = cluster;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
    
    
    
}
