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

    private SessionFactory sessionFactory = null;

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
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Istuntotehtaan luonti ei onnistunut: " + e.getMessage());
            System.exit(-1);
        }

    }

    @Override
    protected void finalize() {

        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HISTORY
    public boolean addHistoryRow(History history) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(history);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;

            }
            return false;
        }
    }

    public History getHistoryRow(int gameNumber) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        History historyRow = new History();

        try {
            session.load(historyRow, gameNumber);
            session.getTransaction().commit();
        } catch (ObjectNotFoundException e) {
            System.out.println("Haettua pelitulosta ei löytynyt!");
        } finally {
            session.close();
        }
        return historyRow;

    }

    public History[] getAllHistoryRows() {
        Session istunto = sessionFactory.openSession();

        @SuppressWarnings("unchecked")
        List<History> list = istunto.createQuery("from History").getResultList();

        istunto.close();
        History[] returnArray = new History[list.size()];

        return (History[]) list.toArray(returnArray);
    }


    public boolean updateHistoryRow(History historyData) {
        boolean out;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        History history = (History) session.get(History.class, historyData.getGameNumber());
        if (history != null) {
            history.setBet(historyData.getBalance());
            out = true;
        } else {
            System.out.println("Ei löytynyt päivitettävää");
            out = false;
        }
        session.getTransaction().commit();
        session.close();
        return out;
    }


    public boolean deleteHistoryRow(int gameNumber) {
        boolean out;
        Session istunto = sessionFactory.openSession();
        istunto.beginTransaction();
        History historyRow = (History) istunto.get(History.class, gameNumber);
        if (historyRow != null) {
            istunto.delete(historyRow);
            out = true;
        } else {
            System.out.println("Ei löytynyt poistettavaa");
            out = false;
        }
        istunto.getTransaction().commit();
        istunto.close();
        return out;
    }

    // USER
    public boolean addUserRow(User user) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;

            }
            return false;
        }
    }
    public User getUser(int userID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = new User();

        try {
            session.load(user, userID);
            session.getTransaction().commit();
        } catch (ObjectNotFoundException e) {
            System.out.println("Haettua pelitulosta ei löytynyt!");
        } finally {
            session.close();
        }
        return user;

    }
}