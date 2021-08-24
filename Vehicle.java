import java.io.Serializable;

public class Vehicle implements Serializable
{
    private int vehNum;
    private String make;
    private String category;
    private double rentalPrice;
    private boolean availableForRent;
    
    //default constructor
    public Vehicle()
    {
        
    }

    public Vehicle(int vehNum, String m, int cat, double amt, boolean avail)
    {
        setVehNum(vehNum);
        setMake(m);
        setCategory(cat);
        setAvailableForRent(avail);
        setRentalPrice(amt);
    }
    
    //constructor that takes 4 arguments to initialize the instance variables
    public Vehicle(String m, int cat, double amt, boolean avail)
    {
        setMake(m);
        setCategory(cat);
        setAvailableForRent(avail);
        setRentalPrice(amt);
    }
    public Vehicle(String m, int cat)
    {
        setMake(m);
        setCategory(cat);
        setAvailableForRent(true);
    }


    // set methods

    public void setVehNum(int vehNum)
    {
        this.vehNum = vehNum;
    }

    public void setMake(String m)
    {
        make = m;
    }
    public void setRentalPrice(double amt)
    {
        rentalPrice = amt;
    }
    
    public void setCategory(int sCategory)
    {
        switch(sCategory)
        {
            case 1:
                category = "Sedan";
                rentalPrice = 450;
                break;
            case 2:
                category = "SUV";
                rentalPrice = 500;
                break;
        }
    }
    
    public void setAvailableForRent(boolean a)
    {
        availableForRent = a;
    }
    
    
    //get methods

    public int getVehNum()
    {
        return vehNum;
    }

    public String getMake()
    {
        return make;
    }
    
    public String getCategory()
    {
        return category;
    }
    public double getRentalPrice()
    {
        return rentalPrice;
    }
      
    //checks if the vehicle is available
    public boolean isAvailableForRent()
    {
        return availableForRent;
    }
       
    public String toString()
    {
        return String.format("%-35s\t%-10s\t%.2f\t\t%-6s",getMake(),getCategory(), getRentalPrice(), new Boolean(isAvailableForRent()).toString());
    }
}
