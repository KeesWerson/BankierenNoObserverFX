package bankierenTest;

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

    @Before
    public void setUp() throws Exception {
        Klant klant1 = new Klant("Jim Sanders", "Nuth");
        Rekening rekening1 = new Rekening(1337,klant1,"Euro");
    }


    @Test
    public void testOpenRekeningNieuweKlant(){
        // creatie van een nieuwe bankrekening met een identificerend rekeningnummer;
        Klant klant2 = new Klant("Hans Worst", "Darmstadt");
        Money money2 = new Money(100000,"Euro");
        Rekening rekening2 = new Rekening(100,klant2,money2);

        Assert.assertEquals("Rekeningnummer klopt niet.",100,rekening2.getNr());
        Assert.assertEquals("Klantnaam klopt niet.","Hans Worst",rekening2.getEigenaar().getNaam());
        Assert.assertEquals("Klantplaats klopt niet.", "Darmstadt", rekening2.getEigenaar().getPlaats());
        Assert.assertEquals("Saldo klopt niet.", money2, rekening2.getSaldo());

    }

    @Test
    public void testOpenRekeningBestaandeKlant(){
        //creatie van een nieuwe bankrekening met een identificerend rekeningnummer;
        //alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat
        //wordt er ook een nieuwe klant aangemaakt

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
