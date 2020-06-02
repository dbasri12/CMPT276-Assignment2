package cmpt276.as1.assignment2test.model;


public class Lens {
    private String name;
    private double aperture;
    private int focalLenght;

    public Lens(String name, double aperture, int focalLenght){
        this.name=name;
        this.aperture=aperture;
        this.focalLenght=focalLenght;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getFocalLenght(){
        return focalLenght;
    }
    public void setFocalLenght(int focalLenght){
        this.focalLenght=focalLenght;
    }
    public double getAperture(){
        return aperture;
    }
    public void setAperture(double aperture){
        this.aperture=aperture;
    }

    @Override
    public String toString() {
        return name +" "+  focalLenght+ "mm " + "F"+aperture;
    }

}
