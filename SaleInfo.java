public class SaleInfo
{
    private int itemsSold;
    private double totalSalesValue;

    public SaleInfo()
    {

    }

    public void incrementItemsSold()
    {
        itemsSold++;
    }

    public void incrementItemsSold(int quantity)
    {
        itemsSold += quantity;
    }

    public void incrementTotalSalesValue(double totalSalesValue)
    {
        this.totalSalesValue += totalSalesValue;
    }

    public void decrementItemsSold()
    {
        itemsSold--;
    }

    public void decrementItemsSold(int quantity)
    {
        itemsSold -= quantity;
    }

    public void decrementTotalSalesValue(double totalSalesValue)
    {
        this.totalSalesValue -= totalSalesValue;
    }

    public int getItemsSold()
    {
        return itemsSold;
    }

    public double getTotalSalesValue()
    {
        return totalSalesValue;
    }
}
