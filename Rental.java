import java.io.Serializable;
import java.util.Date;

public class Rental implements Serializable
{
    private int rentalNumber;
    private Date dateRented;
    private Date dateReturned;
    private double pricePerDay;
    private double totalRental;
    private int custNumber;
    private int vehNumber;

    public Rental()
    {

    }

    public Rental(Date dateRented, double pricePerDay, int custNumber, int vehNumber)
    {
        this.dateRented = dateRented;
        this.pricePerDay = pricePerDay;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
    }

    public Rental(int rentalNumber, Date dateRented, double pricePerDay, int custNumber, int vehNumber)
    {
        this.rentalNumber = rentalNumber;
        this.dateRented = dateRented;
        this.pricePerDay = pricePerDay;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
    }

    public Rental(int rentalNumber, Date dateRented, Date dateReturned, double pricePerDay, int custNumber, int vehNumber)
    {
        this.rentalNumber = rentalNumber;
        this.dateRented = dateRented;
        this.pricePerDay = pricePerDay;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
        setDateReturned(dateReturned);
    }

    public Rental(int rentalNumber, java.sql.Date dateRented, java.sql.Date dateReturned, double pricePerDay, int custNumber, int vehNumber)
    {
        this.rentalNumber = rentalNumber;
        this.dateRented = new Date(dateRented.getTime());
        this.pricePerDay = pricePerDay;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
        setDateReturned(dateReturned);
    }

    public void setRentalNumber(int rentalNumber)
    {
        this.rentalNumber = rentalNumber;
    }

    public void setDateRented(Date dateRented)
    {
        this.dateRented = dateRented;
    }

    public void setDateReturned(Date dateReturned)
    {
        this.dateReturned = dateReturned;
        if(dateReturned == null)
        {
            totalRental = dayDifference(dateRented, new Date()) * pricePerDay;
        }
        else
        {
            totalRental = dayDifference(dateRented, this.dateReturned) * pricePerDay;
        }
    }

    public void setDateReturned(java.sql.Date dateReturned)
    {
        if(dateReturned == null)
        {
            this.dateReturned = null;
        }
        else
        {
            setDateReturned(new Date(dateReturned.getTime()));
        }
    }

    public void setPricePerDay(double pricePerDay)
    {
        this.pricePerDay = pricePerDay;
    }

    public void setTotalRental(double totalRental)
    {
        this.totalRental = totalRental;
    }

    public void setCustNumber(int custNumber)
    {
        this.custNumber = custNumber;
    }

    public void setVehNumber(int vehNumber)
    {
        this.vehNumber = vehNumber;
    }

    public int getRentalNumber()
    {
        return rentalNumber;
    }

    public java.sql.Date getDateRented()
    {
        if(dateRented != null)
        {
            return new java.sql.Date(dateRented.getTime());
        }

        return null;
    }

    public java.sql.Date getDateReturned()
    {
        if(dateReturned != null)
        {
            return new java.sql.Date(dateReturned.getTime());
        }

        return null;
    }

    public double getPricePerDay()
    {
        return pricePerDay;
    }

    public double getTotalRental()
    {
        return totalRental;
    }

    public int getCustNumber()
    {
        return custNumber;
    }

    public int getVehNumber()
    {
        return vehNumber;
    }

    private int dayDifference(Date date1, Date date2)
    {
        int diff = (int)( (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)+1);
        if(diff == 0)
        {
            diff++;
        }
        return diff;
    }
}
