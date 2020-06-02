package cmpt276.as1.assignment2test.model;

public class DOFcalc {
    private double nearFP;
    private double farFP;
    private double HFocal;
    private double DoF;

    public DOFcalc(Lens lens,double distance,double usedAperture, double CoC){
        this.HFocal=(lens.getFocalLenght()*lens.getFocalLenght()) / (usedAperture*CoC);
        this.nearFP=(HFocal*(distance*1000))/(HFocal+((distance*1000)-lens.getFocalLenght()));
        if(distance*1000>HFocal){
            this.farFP=Double.POSITIVE_INFINITY;
            this.DoF=Double.POSITIVE_INFINITY;
        }
        else{
            this.farFP=(HFocal*(distance*1000))/(HFocal-((distance*1000)-lens.getFocalLenght()));
            this.DoF=farFP-nearFP;
        }
    }

    public double getDoF() {
        return DoF;
    }

    public double getFarFP() {
        return farFP;
    }

    public double getHFocal() {
        return HFocal;
    }

    public double getNearFP() {
        return nearFP;
    }

}
