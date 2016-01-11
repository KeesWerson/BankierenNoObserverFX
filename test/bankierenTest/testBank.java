package bankierenTest;

import bank.bankieren.Bank;
import bank.bankieren.Klant;
import bank.bankieren.Money;
import bank.bankieren.Rekening;
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

        Assert.assertEquals("Rekening niet goed aangemaakt.",100000000,bank.openRekening("Hans Worst","Darmstadt"));
        Assert.assertEquals("Rekening niet goed aangemaakt.", 100000001, bank.openRekening("Henk Penck", "Swekdorp"));
        Assert.assertEquals("Rekening toch aangemaakt.", -1, bank.openRekening("",""));
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

        Assert.assertEquals("Rekening niet goed aangemaakt.",100000000,bank.openRekening("Hans Worst","Darmstadt"));
        Assert.assertEquals("Rekening niet goed aangemaakt.", 100000001, bank.openRekening("Hans Worst","Darmstadt"));

    }




    @Test
    public void testMaakOver(){

    }

    @Test
    public void testGetRekening(){

    }

    @Test
    public void testGetName(){

    }
}
