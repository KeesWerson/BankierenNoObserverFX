package bankierenTest;

import bank.bankieren.Bank;
import bank.bankieren.Klant;
import bank.bankieren.Money;
import bank.bankieren.Rekening;
import fontys.util.NumberDoesntExistException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
    public void testMaakOver() throws NumberDoesntExistException {
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
    public void testGetRekening(){

    }

    @Test
    public void testGetName(){

    }
}
