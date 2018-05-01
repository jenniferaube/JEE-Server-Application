package cst8277assignment4consoleclient_corba;

/** File: CST8277Assignment4ConsoleClient_CORBA.java
* Author: John Ferguson
* Date: April 2018
* CORBA Connectivity based on earlier work provided by Todd Kelley
* (2016) Personal Communication
 */
import business.FishStickFacadeRemote;
import enitity.FishStick;
import java.util.UUID;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Stanley Pieda
 */
public class CST8277Assignment4ConsoleClient_CORBA {

    public static void main(String[] args) {
        FishStickFacadeRemote session = null;
// CORBA properties and values and lookup taken after earlier work provided by
// Todd Kelley (2016) Personal Communication
        System.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        try {
            System.out.println("about to try for a session...");
            InitialContext ic = new InitialContext();
            session = (FishStickFacadeRemote) ic.lookup("java:global/CST8277Assignment4/CST8277Assignment4-ejb/FishStickFacade");
            System.out.println("Got a session :) ");
            System.out.println("Creating and inserting a FishStick record into database");
            FishStick stuff = new FishStick();
            stuff.setRecordNum(2);
            stuff.setLambda("Lambda 123");
            stuff.setOmega("Omega 123");
            stuff.setUuid(UUID.randomUUID().toString());
            session.create(stuff);
            System.out.println("Listing contents of database");
            for (FishStick s : session.findAll()) {
                System.out.printf(
                        "id:%d Lambda:%s Omega:%s UUID:%s Record Number:%d%n",
                        s.getId(), s.getLambda(), s.getOmega(), s.getUuid(), s.getRecordNum());
            }
        } catch (NamingException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
