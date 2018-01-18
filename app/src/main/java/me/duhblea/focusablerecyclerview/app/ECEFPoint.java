package me.duhblea.focusablerecyclerview.app;

class ECEFPoint {

    private String name;
    private Double x;
    private Double y;
    private Double z;

     ECEFPoint(String name, Double x, Double y, Double z)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
     String getName() {
        return name;
    }

     Double getX() {
        return x;
    }

     Double getY() {
        return y;
    }

     Double getZ() {
        return z;
    }


}
