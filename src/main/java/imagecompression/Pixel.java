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

import java.util.Objects;

/**
 *
 * @author Jakub Gurgacz
 */
public class Pixel extends BasePixel {

    private ClusterCenter cluster;
    private ClusterCenter oldCluster;
    private int x;
    private int y;

    public Pixel(int alpha, int red, int green, int blue, int x, int y) {
        super(alpha, red, green, blue);
        this.cluster = null;
        this.x = x;
        this.y = y;
        this.oldCluster = null;
    }
    
    public void setValuesAsCluster(){
        try{
        setAlpha(cluster.getAlpha());
        setRed(cluster.getRed());
        setGreen(cluster.getGreen());
        setBlue(cluster.getBlue());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pixel)) return false;
        if (!super.equals(o)) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y &&
                cluster.equals(pixel.cluster) &&
                Objects.equals(oldCluster, pixel.oldCluster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cluster, oldCluster, x, y);
    }
}
