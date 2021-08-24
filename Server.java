import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Server()
    {
        try
        {
            socket = new Socket("127.0.0.1", 4000);

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public ArrayList<Customer> getCustomers()
    {
        try
        {
            out.writeObject("getCustomers");
            out.flush();

            return (ArrayList<Customer>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public ArrayList<Vehicle> getVehicles()
    {
        try
        {
            out.writeObject("getVehicles");
            out.flush();

            return (ArrayList<Vehicle>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public ArrayList<Rental> getRentals()
    {
        try
        {
            out.writeObject("getRentals");
            out.flush();

            return (ArrayList<Rental>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public boolean addCustomer(Customer customer)
    {
        try
        {
            out.writeObject("addCustomer");
            out.flush();
            out.writeObject(customer);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean addVehicle(Vehicle vehicle)
    {
        try
        {
            out.writeObject("addVehicle");
            out.flush();
            out.writeObject(vehicle);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean idNumberExist(String idNumber)
    {
        try
        {
            out.writeObject("idNumberExist");
            out.flush();
            out.writeObject(idNumber);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean phoneNumberExist(String phoneNumber)
    {
        try
        {
            out.writeObject("phoneNumberExist");
            out.flush();
            out.writeObject(phoneNumber);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean makeExist(String make)
    {
        try
        {
            out.writeObject("makeExist");
            out.flush();
            out.writeObject(make);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean rent(Rental rental)
    {
        try
        {
            out.writeObject("rent");
            out.flush();
            out.writeObject(rental);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public boolean returnRental(Rental rental)
    {
        try
        {
            out.writeObject("return");
            out.flush();
            out.writeObject(rental);
            out.flush();

            return in.readBoolean();
        }
        catch (IOException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<String> getRentalDates()
    {
        try
        {
            out.writeObject("getRentalDates");
            out.flush();

            return (ArrayList<String>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
}
