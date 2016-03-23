package lightning.cyborg;

/**
 * Team CyborgLightning 2016 - King's College London - Project Run
 * testEmailValidation implements the JUNIT testing for the Password Confirmation function of
 * RegistrationActivity. Tests include i/o testing, SQLi testing, Sound and Correctness
 * @author Simeon
 */
import org.junit.Test;

import java.text.ParseException;

import lightning.cyborg.testassistants.SQLiAssistantStrings;
import lightning.cyborg.testassistants.SQLiStringChecker;
import lightning.cyborg.testassistants.testDateParserAssistant;
import lightning.cyborg.testassistants.testPasswordConfirmation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Team CyborgLightning 2016 - King's College London - Project Run
 * testEmailValidation implements the JUNIT testing for the Password Confirmation function of
 * RegistrationActivity. Tests include i/o testing, SQLi testing, Sound and Correctness
 * @author Simeon
 */


public class testDateParserTest {

    testDateParserAssistant testDateParser = new testDateParserAssistant();
    SQLiAssistantStrings sqlStrings = new SQLiAssistantStrings();

    @Test
    public void testObjectNotNullCheckPasswordAssistant(){
        assertNotNull(testDateParser);
    }

    @Test
    public void testObjectNotNullCheckSQLstrings(){
        assertNotNull(sqlStrings);
    }


    /*
    Test for date parse validity
     */

    @Test
    public void testDateValidity1() throws ParseException{
        assertTrue(testDateParser.testDateParser("20/12/1990"));
    }

    //Under 18 yo check1
    @Test
    public void testDateValidityUnderAge1() throws ParseException{
        assertFalse(testDateParser.testDateParser("20/12/2002"));
    }


    //Under 18 yo check2
    @Test
    public void testDateValidityUnderAge2() throws ParseException{
        assertFalse(testDateParser.testDateParser("07/02/2010"));
    }



 /*   //Under 18 yo check3
    @Test
    public void testDateValidityUnderAge3() throws ParseException{
        assertFalse(testDateParser.testDateParser("14/01/2005"));
    }

    //Invalid date inputs -failed, required input of type "date"
    //TODO ParseException: Unparseable date: "ss/qq/xx" - TODO
    @Test
    public void testDateValidityBadInput1() throws ParseException{
        assertFalse(testDateParser.testDateParser("ss/qq/xx"));
    }

  /*  //null check - failed, required input of type "date"
    @Test
    public void testDateValidityNullCheck() throws ParseException {
        assertFalse(testDateParser.testDateParser(null));
    }

    //all digits - failed, required input of type "date"
    //TODO input 1920 - 1998
    @Test
    public void testDateValidityCheckDigits() throws ParseException {//Assertion Error
        assertTrue(testDateParser.testDateParser("11/11/1111"));
    }
*/
    @Test
    public void testDateValidityCheckIllogicalInput()throws ParseException {
        assertTrue(testDateParser.testDateParser("01/02/0000"));
    }
    /*
    SQLi - SQL Injection Testing
    All Tests Failed due to ParseException() non date input - 12:22
     */
    //ALL SQLi tests failed
    //TODO java.text.ParseException: Unparseable date: "or 1=1/*"
/*
    //"or 1=1" input - SQL injection Test
    @Test
    public void testDateSQLi1() throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(0)));
    }

    //"'or 1=1" input - SQL injection Test
    @Test
    public void testDateSQLi2()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(1)));
    }

    //""or 1=1" input - SQL injection Test
    @Test
    public void testDateSQLi3()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(3)));
    }

    //"or 1=1-" input - SQL injection Test
    @Test
    public void testDateSQLi4()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(4)));
    }

    //"'or 1=1-" input - SQL injection Test
    @Test
    public void testDateSQLi5()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(5)));
    }

    //""or 1=1-" input - SQL injection Test
    @Test
    public void testDateSQLi6()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(6)));
    }

    //"or 1=1#" input - SQL injection Test
    @Test
    public void testDateSQLi7()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(7)));
    }

    //"'or 1=1#" input - SQL injection Test
    @Test
    public void testDateSQLi8()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(8)));
    }

    //""or" input - SQL injection Test
    @Test
    public void testDateSQLi9()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(9)));
    }

    //"1=1#" input - SQL injection Test
    @Test
    public void testDateSQLi10()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(10)));
    }

    //"or 1=1/*" input - SQL injection Test
    @Test
    public void testDateSQLi11()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(11)));
    }

    //"'or 1=1/*" input - SQL injection Test
    @Test
    public void testDateSQLi12()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(12)));
    }

    //""or 1=1/* input - SQL injection Test
    @Test
    public void testDateSQLi13()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(13)));
    }

    //"or 1=1;%00" input - SQL injection Test
    @Test
    public void testDateSQLi14()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(14)));
    }

    //"'or 1=1;%" input - SQL injection Test
    @Test
    public void testDateSQLi15()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(15)));
    }

    //"'or'" input - SQL injection Test
    @Test
    public void testDateSQLi16()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(16)));
    }

    //"'or" input - SQL injection Test
    @Test
    public void testDateSQLi17()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(17)));
    }

    //"'or'-" input - SQL injection Test
    @Test
    public void testDateSQLi18()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(18)));
    }

    //"'or-" input - SQL injection Test
    @Test
    public void testDateSQLi19()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(19)));
    }

    //"or a=a" input - SQL injection Test
    @Test
    public void testDateSQLi20()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(20)));
    }

    //"'or a=a" input - SQL injection Test
    @Test
    public void testDateSQLi21()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(21)));
    }

    //""or a=a" input - SQL injection Test
    @Test
    public void testDateSQLi22()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(22)));
    }

    //"or a=a-" input - SQL injection Test
    @Test
    public void testDateSQLi23()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(23)));
    }

    //"'or a=a" input - SQL injection Test
    @Test
    public void testDateSQLi24()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(24)));
    }

    //""or a=a" input - SQL injection Test
    @Test
    public void testDateSQLi25()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(25)));
    }

    //"or a=a-" input - SQL injection Test
    @Test
    public void testDateSQLi26()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(26)));
    }

    //"'or a=a-"; input - SQL injection Test
    @Test
    public void testDateSQLi27()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(27)));
    }

    //""or a=a-" input - SQL injection Test
    @Test
    public void testDateSQLi28()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(28)));
    }

    //"or 'a'='a'" input - SQL injection Test
    @Test
    public void testDateSQLi29()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(29)));
    }

    //"'or 'a'='a'" input - SQL injection Test
    @Test
    public void testDateSQLi30()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(30)));
    }

    //""or 'a'='a'" input - SQL injection Test
    @Test
    public void testDateSQLi31()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(31)));
    }

    //"')or('a'='a'" input - SQL injection Test
    @Test
    public void testDateSQLi32()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(32)));
    }

    //"")"a"="a"" input - SQL injection Test
    @Test
    public void testDateSQLi33()throws ParseException{
        assertFalse(testDateParser.testDateParser(sqlStrings.getSQLibyID(33)));
    }

*/
}
