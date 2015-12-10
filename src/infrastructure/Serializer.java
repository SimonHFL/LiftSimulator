package infrastructure;

import java.io.*;

public class Serializer
{

    static String filePath = "tmp/lifts.ser";

    /**
     * Serialize the object and save to a file
     * @param object
     */
    public static void save(Serializable object)
    {
        try
        {
            File f = new File(filePath);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    /**
     * Load a serialized object from a file
     * @return
     */
    public static Object load()
    {
        try
        {
            File f = new File(filePath);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();

        }catch(IOException | ClassNotFoundException i)
        {
            System.out.println("File not found");
        }

        return null;
    }

    /**
     * Deletes the file with the serialized object
     */
    public static void clear() {
        new File(filePath).delete();
    }
}
