package internettoegang;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

/**
 * Created by Kees on 11/01/2016.
 */
public class testBankiersessie {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsGeldig(){
        /**
         * @returns true als de laatste aanroep van getRekening of maakOver voor deze
         *          sessie minder dan GELDIGHEIDSDUUR geleden is
         *          en er geen communicatiestoornis in de tussentijd is opgetreden,
         *          anders false
         */

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
