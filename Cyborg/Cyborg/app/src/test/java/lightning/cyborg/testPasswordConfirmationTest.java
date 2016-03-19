package lightning.cyborg;

/**
 * Team CyborgLightning 2016 - King's College London - Project Run
 * testPasswordConfirmation implements the JUNIT testing for the Password Confirmation function of
 * RegistrationActivity. Tests include i/o testing, SQLi testing, Sound and Correctness
 * @author Simeon
 */
import org.junit.Test;

import lightning.cyborg.testassistants.SQLiAssistantStrings;
import lightning.cyborg.testassistants.testPasswordConfirmation;

import static org.junit.Assert.*;

public class testPasswordConfirmationTest {

    SQLiAssistantStrings sqlStrings = new SQLiAssistantStrings();
    testPasswordConfirmation testPassword = new testPasswordConfirmation();
    //object notNull() check
    @Test
    public void testObjectNotNullCheckPasswordAssistant(){
        assertNotNull(testPassword);
    }
    @Test
    public void testObjectNotNullCheckSQLstrings(){
        assertNotNull(sqlStrings);
    }

    /*
    Tests For Password equivalence (password1 = password2)
    All Tests Passed - update 14:44 19/03/2016
     */
    @Test
    public void testPasswordEquivalenceTrue(){
        assertTrue(testPassword.testPasswordValidationParser("team_cyborg", "team_cyborg"));
    }

    @Test
    public void testPasswordEquivalenceFalse(){
        assertFalse(testPassword.testPasswordValidationParser("team_","_maet"));
    }

    /*
     Tests for null object password input
     All Tests Passed - update 14:50 19/03/2016
     */

    @Test
    public void testPasswordNotNullInputFirst(){
        assertFalse(testPassword.testPasswordValidationParser("", "moni"));
    }

    @Test
    public void testPasswordNotNullInputSecond(){
        assertFalse(testPassword.testPasswordValidationParser("stephen",""));
    }

    @Test
    public void testPasswordNotNullBoth(){
        assertFalse(testPassword.testPasswordValidationParser("", ""));
    }

    /*
     Test for alphanumeric and symbol checks, lower and upper case
     All Tests Passed - 14:58: 19/03/2016
     */

    @Test
    public void testPasswordAlphaNumericBasic(){
        assertTrue(testPassword.testPasswordValidationParser("simeon123","simeon123"));
    }

    @Test
    public void testPasswordAlphaNumericAdvanced(){
        assertTrue(testPassword.testPasswordValidationParser("4m4zInG_221","4m4zInG_221"));
    }

    @Test
    public void testPasswordAlphaNumericandSymbols(){
        assertTrue(testPassword.testPasswordValidationParser("££SanSte7an0","££SanSte7an0"));
    }

    @Test
    public void testPasswordUpperLowerCaseValid(){
        assertTrue(testPassword.testPasswordValidationParser("ASTROLOGY", "ASTROLOGY"));
    }

    @Test
    public void testPasswordUpperLowerCaseNotValidFirst(){
        assertFalse(testPassword.testPasswordValidationParser("stephen", "STEPHEN"));
    }

    @Test
    public void testPasswordUpperLowerCaseNotValidSecond(){
        assertFalse(testPassword.testPasswordValidationParser("STEPHEN", "stephen"));
    }

    //
    @Test
    public void testPasswordOnlyNumeric(){
        assertTrue(testPassword.testPasswordValidationParser("1234567", "1234567"));
    }

    /*
    SQLi - SQL Injection Testing
    All Tests Passed - 12:22
     */

    //"or 1=1" input - SQL injection Test
    @Test
    public void testPasswordSQL1(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(0),sqlStrings.getSQLibyID(0)));
    }

    //"'or 1=1" input - SQL injection Test
    @Test
    public void testPasswordSQL2(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(1), sqlStrings.getSQLibyID(1)));
    }

    //""or 1=1" input - SQL injection Test
    @Test
    public void testPasswordSQL3(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(3),sqlStrings.getSQLibyID(3)));
    }

    //"or 1=1-" input - SQL injection Test
    @Test
    public void testPasswordSQL4(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(4), sqlStrings.getSQLibyID(4)));
    }

    //"'or 1=1-" input - SQL injection Test
    @Test
    public void testPasswordSQL5(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(5), sqlStrings.getSQLibyID(5)));
    }

    //""or 1=1-" input - SQL injection Test
    @Test
    public void testPasswordSQL6(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(6), sqlStrings.getSQLibyID(6)));
    }

    //"or 1=1#" input - SQL injection Test
    @Test
    public void testPasswordSQL7(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(7), sqlStrings.getSQLibyID(7)));
    }

    //"'or 1=1#" input - SQL injection Test
    @Test
    public void testPasswordSQL8(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(8),sqlStrings.getSQLibyID(8)));
    }

    //""or" input - SQL injection Test
    @Test
    public void testPasswordSQL9(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(9),sqlStrings.getSQLibyID(9)));
    }

    //"1=1#" input - SQL injection Test
    @Test
    public void testPasswordSQL10(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(10), sqlStrings.getSQLibyID(10)));
    }

    //"or 1=1/*" input - SQL injection Test
    @Test
    public void testPasswordSQL11(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(11),sqlStrings.getSQLibyID(11)));
    }

    //"'or 1=1/*" input - SQL injection Test
    @Test
    public void testPasswordSQL12(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(12), sqlStrings.getSQLibyID(12)));
    }

    //""or 1=1/* input - SQL injection Test
    @Test
    public void testPasswordSQL13(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(13),sqlStrings.getSQLibyID(13)));
    }

    //"or 1=1;%00" input - SQL injection Test
    @Test
    public void testPasswordSQL14(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(14),sqlStrings.getSQLibyID(14)));
    }

    //"'or 1=1;%" input - SQL injection Test
    @Test
    public void testPasswordSQL15(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(15),sqlStrings.getSQLibyID(15)));
    }

    //"'or'" input - SQL injection Test
    @Test
    public void testPasswordSQL16(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(16),sqlStrings.getSQLibyID(16)));
    }

    //"'or" input - SQL injection Test
    @Test
    public void testPasswordSQL17(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(17),sqlStrings.getSQLibyID(17)));
    }

    //"'or'-" input - SQL injection Test
    @Test
    public void testPasswordSQL18(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(18),sqlStrings.getSQLibyID(18)));
    }

    //"'or-" input - SQL injection Test
    @Test
    public void testPasswordSQL19(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(19),sqlStrings.getSQLibyID(19)));
    }

    //"or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL20(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(20),sqlStrings.getSQLibyID(20)));
    }

    //"'or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL21(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(21),sqlStrings.getSQLibyID(21)));
    }

    //""or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL22(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(22), sqlStrings.getSQLibyID(22)));
    }

    //"or a=a-" input - SQL injection Test
    @Test
    public void testPasswordSQL23(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(23),sqlStrings.getSQLibyID(23)));
    }

    //"'or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL24(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(24),sqlStrings.getSQLibyID(24)));
    }

    //""or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL25(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(25),sqlStrings.getSQLibyID(25)));
    }

    //"or a=a-" input - SQL injection Test
    @Test
    public void testPasswordSQL26(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(26),sqlStrings.getSQLibyID(26)));
    }

    //"'or a=a-"; input - SQL injection Test
    @Test
    public void testPasswordSQL27(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(27),sqlStrings.getSQLibyID(27)));
    }

    //""or a=a-" input - SQL injection Test
    @Test
    public void testPasswordSQL28(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(28),sqlStrings.getSQLibyID(28)));
    }

    //"or 'a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL29(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(29),sqlStrings.getSQLibyID(29)));
    }

    //"'or 'a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL30(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(30),sqlStrings.getSQLibyID(30)));
    }

    //""or 'a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL31(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(31),sqlStrings.getSQLibyID(31)));
    }

    //"')or('a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL32(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(32),sqlStrings.getSQLibyID(32)));
    }

    //"")"a"="a"" input - SQL injection Test
    @Test
    public void testPasswordSQL33(){
        assertFalse(testPassword.testPasswordValidationParser(sqlStrings.getSQLibyID(33),sqlStrings.getSQLibyID(33)));
    }


}
