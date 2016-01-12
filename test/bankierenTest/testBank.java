package bankierenTest;

import bank.bankieren.Bank;
import bank.bankieren.Klant;
import bank.bankieren.Money;
import bank.bankieren.Rekening;
import fontys.util.NumberDoesntExistException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 * Created by Kees on 11/01/2016.
 */
public class testBank extends TestCase {

    private Bank bank;

    @Before
    public void setUp() throws Exception {
        bank = new Bank("Rabobank");
        bank.openRekening("Jim Sanders","Nuth");
        bank.openRekening("Kees Werson","Valkenswaard");

    }

    @Test
    public void testOpenRekeningNieuweKlant(){
        /**
         * creatie van een nieuwe bankrekening met een identificerend rekeningnummer;
         * alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat
         * wordt er ook een nieuwe klant aangemaakt
         *
         * @param naam
         *            van de eigenaar van de nieuwe bankrekening
         * @param plaats
         *            de woonplaats van de eigenaar van de nieuwe bankrekening
         * @return -1 zodra naam of plaats een lege string en anders het nummer van de
         *         gecreeerde bankrekening
         */

        Assert.assertEquals("Rekening niet goed aangemaakt.",100000002,bank.openRekening("Hans Worst","Darmstadt"));

        Klant klant = (Klant)bank.getRekening(100000002).getEigenaar();
        Assert.assertEquals("Klant niet goed aangemaakt", "Hans Worst", klant.getNaam());
        Assert.assertEquals("Klant niet goed aangemaakt", "Darmstadt", klant.getPlaats());

        Assert.assertEquals("Rekening toch aangemaakt.", -1, bank.openRekening("",""));
        Assert.assertEquals("Rekening toch aangemaakt.", -1, bank.openRekening("Piet",""));
        Assert.assertEquals("Rekening toch aangemaakt.", -1, bank.openRekening("","Dorp"));
    }

    @Test
    public void testOpenRekeningBestaandeKlant(){
        /**
         * creatie van een nieuwe bankrekening met een identificerend rekeningnummer;
         *
         * @param naam
         *            van de eigenaar van de nieuwe bankrekening
         * @param plaats
         *            de woonplaats van de eigenaar van de nieuwe bankrekening
         * @return -1 zodra naam of plaats een lege string en anders het nummer van de
         *         gecreeerde bankrekening
         */

        Assert.assertEquals("Rekening niet goed aangemaakt.",100000002,bank.openRekening("Hans Worst","Darmstadt"));
        Assert.assertEquals("Rekening niet goed aangemaakt.", 100000003, bank.openRekening("Hans Worst","Darmstadt"));

    }




    @Test
    public void testMaakOverSucces() throws NumberDoesntExistException {
        /**
         * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
         * bankrekening met nummer bestemming.
         *
         * @param bron
         * @param bestemming
         *            ongelijk aan bron
         * @param bedrag
         *            is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException
         *             als een van de twee bankrekeningnummers onbekend is
         */

        boolean gelukt = bank.maakOver(100000000,100000001,new Money(1000,"€"));

        Assert.assertEquals("Geld niet goed bijgeschreven.",1000,bank.getRekening(100000001).getSaldo().getCents());
        Assert.assertEquals("Geld niet goed afgeschreven.",-1000,bank.getRekening(100000000).getSaldo().getCents());
        Assert.assertEquals("False teruggegeven.", true, gelukt);

    }

    @Test
    public void testMaakOverFail() throws NumberDoesntExistException {
        /**
         * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
         * bankrekening met nummer bestemming, mits het afschrijven van het bedrag
         * van de rekening met nr bron niet lager wordt dan de kredietlimiet van deze
         * rekening
         *
         * @param bron
         * @param bestemming
         *            ongelijk aan bron
         * @param bedrag
         *            is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException
         *             als een van de twee bankrekeningnummers onbekend is
         */

        boolean gelukt = bank.maakOver(100000000,100000001,new Money(10001,"€"));

        Assert.assertEquals("Geld toch bijgeschreven.",0,bank.getRekening(100000001).getSaldo().getCents());
        Assert.assertEquals("Geld toch afgeschreven.",0,bank.getRekening(100000000).getSaldo().getCents());
        Assert.assertEquals("True teruggegeven.", false, gelukt);

    }

    @Test
    public void testMaakOverOnbekendeRekening() throws NumberDoesntExistException {
        /**
         * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
         * bankrekening met nummer bestemming.
         *
         * @param bron
         * @param bestemming
         *            ongelijk aan bron
         * @param bedrag
         *            is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException
         *             als een van de twee bankrekeningnummers onbekend is
         */

        try{
            boolean gelukt = bank.maakOver(100000000,1254789963,new Money(1001,"€"));
            fail("Onbekende bankrekening bestemming wordt toch gebruikt.");
        }
        catch(NumberDoesntExistException e){
            //System.out.println("testMaakOverOnbekendeRekening: NumberDoesntExistException voor bestemming succesvol opgegooid.");
        }

        try{
            boolean gelukt = bank.maakOver(321456987,100000000,new Money(1001,"€"));
            fail("Onbekende bankrekening overmaker wordt toch gebruikt.");
        }
        catch(NumberDoesntExistException e){
            //System.out.println("testMaakOverOnbekendeRekening: NumberDoesntExistException voor overmaker succesvol opgegooid.");
        }

    }

    @Test
    public void testGetRekening(){
        /**
         * @param nr
         * @return de bankrekening met nummer nr mits bij deze bank bekend, anders null
         */

        Assert.assertNotNull(bank.getRekening(100000000));
        Assert.assertEquals("Verkeerde rekening teruggegeven.",100000000,bank.getRekening(100000000).getNr());
        Assert.assertEquals("Verkeerde eigenaar teruggegeven.","Jim Sanders",bank.getRekening(100000000).getEigenaar().getNaam());
        Assert.assertEquals("Verkeerde plaatsnaam teruggegeven.","Nuth",bank.getRekening(100000000).getEigenaar().getPlaats());
    }

    @Test
    public void testGetRekeningNull(){
        /**
         * @param nr
         * @return de bankrekening met nummer nr mits bij deze bank bekend, anders null
         */

        Assert.assertNull(bank.getRekening(321654987));

    }

    @Test
    public void testGetName(){
        /**
         * @return de naam van deze bank
         */

        Assert.assertEquals("Verkeerde banknaam teruggegeven.","Rabobank", bank.getName());
    }
}
