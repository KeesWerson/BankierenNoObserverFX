package internettoegangTest;

import bank.bankieren.Bank;
import bank.bankieren.Money;
import bank.bankieren.Rekening;
import bank.gui.BankierSessieController;
import bank.internettoegang.Balie;
import bank.internettoegang.Bankiersessie;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * Created by Kees on 11/01/2016.
 */
public class testBankiersessie {

    private Bank bankRABO;
    private Balie balieRABO;
    private String accountName;
    private int rekeningNummerGever;
    private int rekeningNummerKrijger;
    private Bankiersessie bSessieRABO;

    @Before
    public void setUp() throws Exception {
        //Nieuwe balie met bijhorende bank aanmaken om te testen.
        bankRABO = new Bank("ING");
        balieRABO = new Balie(bankRABO);

        //creëer een test account
        accountName = balieRABO.openRekening("HansLeeuwen", "Tilburg", "123456");

        //Log in om de sessie te krijgen en gegevens van de 'gever' op te slaan.
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
            rekeningNummerGever = bSessieRABO.getRekening().getNr();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //Log uit.
        try {
            bSessieRABO.logUit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsGeldig(){
        /**
         * @returns true als de laatste aanroep van getRekening of maakOver voor deze
         *          sessie minder dan GELDIGHEIDSDUUR geleden is
         *          en er geen communicatiestoornis in de tussentijd is opgetreden,
         *          anders false
         */

        //Log in
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //Aanroep binnen de GELDIGHEIDSDUUR.
        Assert.assertTrue("Aanroep te laat", bSessieRABO.isGeldig());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Aanroep na de GELDIGHEIDSDUUR.
        Assert.assertFalse("Aanroep te vroeg", bSessieRABO.isGeldig());

        //Log uit.
        try {
            bSessieRABO.logUit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMaakOver(){
        /**
         * er wordt bedrag overgemaakt van de bankrekening met het nummer bron naar
         * de bankrekening met nummer bestemming
         *
         * @param bron
         * @param bestemming
         *            is ongelijk aan rekeningnummer van deze bankiersessie
         * @param bedrag
         *            is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException
         *             als bestemming onbekend is
         * @throws InvalidSessionException
         *             als sessie niet meer geldig is
         */

        //Rekening waar het geld naar over wordt gemaakt.
        String bestemmingRekening = balieRABO.openRekening("Jan", "Veldhoven", "1234");

        //Log opnieuw in en uit voor een nieuwe sessie.
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(bestemmingRekening, "1234");
            rekeningNummerKrijger = bSessieRABO.getRekening().getNr();
            bSessieRABO.logUit();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }

        //Log in op het andere account om de transactie te volgrengen.
        boolean gelukt = false;
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
            gelukt = bSessieRABO.maakOver(rekeningNummerKrijger, new Money(50, "â‚¬"));
        } catch (NumberDoesntExistException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        org.junit.Assert.assertEquals("Geld niet goed bijgeschreven.", 50, bankRABO.getRekening(rekeningNummerKrijger).getSaldo().getCents());
        org.junit.Assert.assertEquals("Geld niet goed afgeschreven.", -50, bankRABO.getRekening(rekeningNummerGever).getSaldo().getCents());
        Assert.assertTrue("Overmaken is niet gelukt", gelukt);
    }

    @Test
    public void testlogUit(){
        /**
         * sessie wordt beeindigd
         */

        //Log opnieuw in en uit om het uitloggen te testen.
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
            Assert.assertTrue("Banksessie is niet correct aangemaakt", bSessieRABO.isGeldig());
            bSessieRABO.logUit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Assert.assertTrue("Banksessie is niet correct afgesloten.", bSessieRABO.isGeldig());
    }

    @Test
    public void testGetRekening(){
        /**
         * @return de rekeninggegevens die horen bij deze sessie
         * @throws InvalidSessionException
         *             als de sessieId niet geldig of verlopen is
         * @throws RemoteException
         */

        Rekening r = null;
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
            r = (Rekening) bSessieRABO.getRekening();
            bSessieRABO.logUit();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Correcte rekening is niet opgehaalt.", r.getEigenaar().getNaam(), "HansLeeuwen");

        //Nu wachten tot dat de sessie verloopt
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean sessionOverExceptie = false;
        try {
            r = (Rekening) bSessieRABO.getRekening();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
            sessionOverExceptie = true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Assert.assertTrue("Sessie is niet correct afgelopen.", sessionOverExceptie);
    }
}
