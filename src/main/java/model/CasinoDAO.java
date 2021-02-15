package model;



import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class CasinoDAO {

    private SessionFactory istuntotehdas = null;

    public CasinoDAO() {

        try {
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            String uname = "", passwd = "";
            InputStream inputStream = null;
            try {
                Properties prop = new Properties();
                String propFileName = "config.properties";

                inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }


                uname = prop.getProperty("username");
                passwd = prop.getProperty("passwd");

            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }

            cfg.getProperties().setProperty("hibernate.connection.password", passwd);
            cfg.getProperties().setProperty("hibernate.connection.username", uname);
            istuntotehdas = cfg.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
            System.exit(-1);
        }

    }

    @Override
    protected void finalize() {

        try {
            if (istuntotehdas != null) {
                istuntotehdas.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addHistoryRow(History history) {
        Transaction transaktio = null;

        try (Session istunto = istuntotehdas.openSession()) {
            transaktio = istunto.beginTransaction();
            istunto.saveOrUpdate(history);
            transaktio.commit();
            return true;
        } catch (Exception e) {
            if (transaktio != null) {
                transaktio.rollback();
                throw e;

            }
            return false;
        }
    }

    public PeliTulos luePeliTulos(String tunnus) {
        Session istunto = istuntotehdas.openSession();
        istunto.beginTransaction();

        PeliTulos tulos = new PeliTulos();

        try {
            istunto.load(tulos, tunnus);
            istunto.getTransaction().commit();
        } catch (ObjectNotFoundException e) {
            System.out.println("Haettua pelitulosta ei löytynyt!");
        } finally {
            istunto.close();
        }
        return tulos;

    }

    public History[] getAllHistoryRows() {
        Session istunto = istuntotehdas.openSession();

        @SuppressWarnings("unchecked")
        List<History> list = istunto.createQuery("from History").getResultList();

        istunto.close();
        History[] returnArray = new History[list.size()];

        return (History[]) list.toArray(returnArray);
    }


    public boolean paivitaPeliTulos(PeliTulos peliTulosData) {
        boolean out;
        Session istunto = istuntotehdas.openSession();
        istunto.beginTransaction();
        PeliTulos peliTulos = (PeliTulos) istunto.get(PeliTulos.class, peliTulosData.getId());
        if (peliTulos != null) {
            peliTulos.setPelaajaVoitti(peliTulosData.isPelaajaVoitti());
            out = true;
        } else {
            System.out.println("Ei löytynyt päivitettävää");
            out = false;
        }
        istunto.getTransaction().commit();
        istunto.close();
        return out;
    }


    public boolean poistaTulos(int id) {
        boolean out;
        Session istunto = istuntotehdas.openSession();
        istunto.beginTransaction();
        PeliTulos tulos = (PeliTulos) istunto.get(PeliTulos.class, id);
        if (tulos != null) {
            istunto.delete(tulos);
            out = true;
        } else {
            System.out.println("Ei löytynyt poistettavaa");
            out = false;
        }
        istunto.getTransaction().commit();
        istunto.close();
        return out;
    }
}