package lightning.cyborg;

import org.junit.Test;

import lightning.cyborg.testassistants.SQLiAssistantStrings;
import lightning.cyborg.testassistants.testEmailValidationAssistant;
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



public class testEmailValidation {
    SQLiAssistantStrings sqlStrings = new SQLiAssistantStrings();
    testEmailValidationAssistant testEmail = new testEmailValidationAssistant();
    //object notNull() check
    @Test
    public void testObjectNotNullCheckEmailValidationAssistant(){
        assertNotNull(testEmail);
    }
    @Test
    public void testObjectNotNullCheckSQLstrings(){
        assertNotNull(sqlStrings);
    }

    /*
    Test for email validity, valid character input
    All Tests Passed - update 14:44 19/03/2016
     */
    @Test
    public void testEmailValidationTestTrue(){
        assertTrue(testEmail.testPasswordValidationParser("crazy_boo@gmail.com"));
    }

    @Test
    public void testEmailValidationTestFalse(){
        assertFalse(testEmail.testPasswordValidationParser("xxxxxx_zz"));
    }

    /*
    Different domain emails for improved testing
     */

    @Test
    public void testPasswordEquivalenceTestORG(){
        assertTrue(testEmail.testPasswordValidationParser("xman9488fili@yahoo.org"));
    }


    @Test
    public void testPasswordEquivalenceTestNET(){
        assertTrue(testEmail.testPasswordValidationParser("sherlock.holmes@outlook.net"));
    }

    @Test
    public void testPasswordEquivalenceTestCOUK(){
        assertTrue(testEmail.testPasswordValidationParser("anthonty_hopkins43@kcl.co.uk"));
    }

    // .com and Upper Case letter test
    @Test
    public void testPasswordEquivalenceTestCOM(){
        assertTrue(testEmail.testPasswordValidationParser("DESTRUCTO@MAIL.COM"));
    }

    //.jp and Japanese alphabet sign input - should return false, app only support EN UK input
    @Test
    public void testPasswordEquivalenceTestJP(){
        assertFalse(testEmail.testPasswordValidationParser("漢字漢字@ummy.jp"));
    }

    //.re and Cyrillic alphabet sign input - should return false, app only support EN UK input
    @Test
    public void testPasswordEquivalenceTestRU(){
        assertFalse(testEmail.testPasswordValidationParser("ящика@apapo.ru"));
    }

    //.dk and Danish alphabet sign input - should return false, app only support EN UK
    @Test
    public void testPasswordEquivalenceTestDK(){
        assertFalse(testEmail.testPasswordValidationParser("ÆÆÆÆ@yahoo.dk"));
    }

    //test
    @Test
    public void testPasswordEquivalenceTestFALSESQLi(){
        assertFalse(testEmail.testPasswordValidationParser("'a=a'@yahoo.org"));
    }

        /*
    SQLi - SQL Injection Testing
    All Tests Passed - 12:22
     */

    //"or 1=1" input - SQL injection Test
    @Test
    public void testPasswordSQL1(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(0) + "@gmail.com"));
    }

    //"'or 1=1" input - SQL injection Test
    @Test
    public void testPasswordSQL2(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(1)+"@yahoo.co.uk"));
    }

    //""or 1=1" input - SQL injection Test
    @Test
    public void testPasswordSQL3(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(2)+"@mail.com"));
    }

    //"or 1=1-" input - SQL injection Test
    @Test
    public void testPasswordSQL4(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(3)+"@outlook.com"));
    }

    //"'or 1=1-" input - SQL injection Test
    @Test
    public void testPasswordSQL5(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(4)+"@kcl.ac.uk"));
    }

    //""or 1=1-" input - SQL injection Test
    @Test
    public void testPasswordSQL6(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(5)+"@hotmail.com"));
    }

    //"or 1=1#" input - SQL injection Test
    @Test
    public void testPasswordSQL7(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(6)+"@gmail.com"));
    }

    //"'or 1=1#" input - SQL injection Test
    @Test
    public void testPasswordSQL8(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(7)+"@gmail.com"));
    }

    //""or" input - SQL injection Test
    @Test
    public void testPasswordSQL9(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(8)+"@gmail.com"));
    }

    //"1=1#" input - SQL injection Test
    @Test
    public void testPasswordSQL10(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(9)+"@gmail.com"));
    }

    //"or 1=1/*" input - SQL injection Test
    @Test
    public void testPasswordSQL11(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(10)+"@gmail.com"));
    }

    //"'or 1=1/*" input - SQL injection Test
    @Test
    public void testPasswordSQL12(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(11)+"@gmail.com"));
    }

    //""or 1=1/* input - SQL injection Test
    @Test
    public void testPasswordSQL13(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(12)+"@gmail.com"));
    }

    //"or 1=1;%00" input - SQL injection Test
    @Test
    public void testPasswordSQL14(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(13)+"@gmail.com"));
    }

    //"'or 1=1;%" input - SQL injection Test
    @Test
    public void testPasswordSQL15(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(14)+"@gmail.com"));
    }

    //"'or'" input - SQL injection Test
    @Test
    public void testPasswordSQL16(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(15)+"@gmail.com"));
    }

    //"'or" input - SQL injection Test
    @Test
    public void testPasswordSQL17(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(16)+"@gmail.com"));
    }

    //"'or'-" input - SQL injection Test
    @Test
    public void testPasswordSQL18(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(17)+"@gmail.com"));
    }

    //"'or-" input - SQL injection Test
    @Test
    public void testPasswordSQL19(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(18)+"@gmail.com"));
    }

    //"or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL20(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(19)+"@gmail.com"));
    }

    //"'or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL21(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(20)+"@gmail.com"));
    }

    //""or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL22(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(21)+"@gmail.com"));
    }

    //"or a=a-" input - SQL injection Test
    @Test
    public void testPasswordSQL23(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(22)+"@gmail.com"));
    }

    //"'or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL24(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(23)+"@gmail.com"));
    }

    //""or a=a" input - SQL injection Test
    @Test
    public void testPasswordSQL25(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(24)+"@gmail.com"));
    }

    //"or a=a-" input - SQL injection Test
    @Test
    public void testPasswordSQL26(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(25)+"@gmail.com"));
    }

    //"'or a=a-"; input - SQL injection Test
    @Test
    public void testPasswordSQL27(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(26)+"@gmail.com"));
    }

    //""or a=a-" input - SQL injection Test
    @Test
    public void testPasswordSQL28(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(27)+"@gmail.com"));
    }

    //"or 'a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL29(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(28)+"@gmail.com"));
    }

    //"'or 'a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL30(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(29)+"@gmail.com"));
    }

    //""or 'a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL31(){
        assertFalse(testEmail.testPasswordValidationParser("steaphain@gmail.com"+sqlStrings.getSQLibyID(30)));
    }

    //"')or('a'='a'" input - SQL injection Test
    @Test
    public void testPasswordSQL32(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(31)+"@gmail.com"));
    }

    //"")"a"="a"" input - SQL injection Test
    @Test
    public void testPasswordSQL33(){
        assertFalse(testEmail.testPasswordValidationParser(sqlStrings.getSQLibyID(32)+"@gmail.com"));
    }

}
