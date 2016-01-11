package internettoegang;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kees on 11/01/2016.
 */
public class testBalie {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testOpenRekening(){
        /**
         * creatie van een nieuwe bankrekening; het gegenereerde bankrekeningnummer is
         * identificerend voor de nieuwe bankrekening en heeft een saldo van 0 euro
         * @param naam van de eigenaar van de nieuwe bankrekening
         * @param plaats de woonplaats van de eigenaar van de nieuwe bankrekening
         * @param wachtwoord van het account waarmee er toegang kan worden verkregen
         * tot de nieuwe bankrekening
         * @return null zodra naam of plaats een lege string of wachtwoord minder dan
         * vier of meer dan acht karakters lang is en anders de gegenereerde
         * accountnaam(8 karakters lang) waarmee er toegang tot de nieuwe bankrekening
         * kan worden verkregen
         */

    }

    @Test
    public void testLogIn(){
        /**
         * er wordt een sessie opgestart voor het login-account met de naam
         * accountnaam mits het wachtwoord correct is
         * @param accountnaam
         * @param wachtwoord
         * @return de gegenereerde sessie waarbinnen de gebruiker
         * toegang krijgt tot de bankrekening die hoort bij het betreffende login-
         * account mits accountnaam en wachtwoord matchen, anders null
         */

    }
}
