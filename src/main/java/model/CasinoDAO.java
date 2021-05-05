package model;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * A class to control Database (Hibernate)
 */
public class CasinoDAO {

    private SessionFactory sessionFactory = null;
    private static boolean testmode = false;

    /**
     * Constructor of CasinoDAO
     */
    public CasinoDAO() {

        try {
            Configuration cfg = new Configuration();
            if (testmode) {
                cfg.configure("hibernate-testDB.cfg.xml");
            } else {
                cfg.configure("hibernate.cfg.xml");
            }

            String uname = "", passwd = "";
            InputStream inputStream = null;
            try {
                Properties prop = new Properties();
                String propFileName = "config.properties";

                inputStream = CasinoDAO.class.getClassLoader().getResourceAsStream(propFileName);

                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }

                if (testmode) {
                    uname = prop.getProperty("test_username");
                    passwd = prop.getProperty("test_passwd");
                } else {
                    uname = prop.getProperty("username");
                    passwd = prop.getProperty("passwd");
                }

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



    /**
     * Closes the sessionFactory if it is not null
     */
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

    /**
     * Adds a row to the historyTable
     *
     * @param history object
     * @return - Returns true if successed and false if not
     */
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

    /**
     * Gets the specific historyRow from historyTable
     *
     * @param gameNumber - Used to search the specific historyRow
     * @return - The searched historyRow
     */
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

    /**
     * Gets every row of the historyTable
     *
     * @return - the whole historyTable
     */
    public History[] getAllHistoryRows() {
        Session istunto = sessionFactory.openSession();

        @SuppressWarnings("unchecked")
        List<History> list = istunto.createQuery("from History").getResultList();

        istunto.close();
        History[] returnArray = new History[list.size()];

        return (History[]) list.toArray(returnArray);
    }

    public User[] getAllUserRows() {
        Session istunto = sessionFactory.openSession();

        @SuppressWarnings("unchecked")
        List<User> list = istunto.createQuery("from User").getResultList();

        istunto.close();
        User[] returnArray = new User[list.size()];

        return (User[]) list.toArray(returnArray);
    }

    /**
     * Updates specific historyRow from historyTable
     *
     * @param historyData to set the data to the Row
     * @return - Returns true if successed and false if not
     */
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

    /**
     * Deletes specific historyRow from historyTable
     *
     * @param gameNumber - Used to search for wanted historyRow
     * @return - Returns true if successed and false if not
     */
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

    /**
     * Adds a userRow to the userTable
     *
     * @param user - User added to the table
     * @return - Returns true if successed and false if not
     */
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


    /**
     * Updates the user's balance at the userTable
     *
     * @param user - User that's balance is wanted to change
     * @return - Returns true if successed and false if not
     */
    public boolean updateUser(User user) {
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

    /**
     * Gets the user by username
     *
     * @param username - Username of the user wanted to find
     * @return the User object
     */
    public User getUserByUsername(String username) {
        Criteria criteria = sessionFactory.openSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        try {
            return (User) criteria.list().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Sets DAO to use test database
     * @param testmode - Boolean is testmode on
     */
    public static void setTestmode(boolean testmode) {
        CasinoDAO.testmode = testmode;
    }
}