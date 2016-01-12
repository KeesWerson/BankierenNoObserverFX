package internettoegangTest;

import bank.bankieren.Bank;
import bank.bankieren.Money;
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
    private int rekeningNummer;
    private Bankiersessie bSessieRABO;

    @Before
    public void setUp() throws Exception {
        //Nieuwe balie met bijhorende bank aanmaken om te testen.
        bankRABO = new Bank("ING");
        balieRABO = new Balie(bankRABO);

        //creëer een test account
        accountName = balieRABO.openRekening("HansLeeuwen", "Tilburg", "123456");

        //Log in om de sessie te krijgen
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
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
            rekeningNummer = bSessieRABO.getRekening().getNr();
            bSessieRABO.logUit();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }

        //Log in op het andere account om de transactie te volgrengen.
        try {
            bSessieRABO = (Bankiersessie) balieRABO.logIn(accountName, "123456");
            bSessieRABO.maakOver(rekeningNummer, new Money(5, "€"));
        } catch (NumberDoesntExistException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testlogUit(){
        /**
         * sessie wordt beeindigd
         */

    }

    @Test
    public void testGetRekening(){
        /**
         * @return de rekeninggegevens die horen bij deze sessie
         * @throws InvalidSessionException
         *             als de sessieId niet geldig of verlopen is
         * @throws RemoteException
         */

    }
}
