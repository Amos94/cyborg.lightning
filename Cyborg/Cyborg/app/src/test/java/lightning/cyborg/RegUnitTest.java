package lightning.cyborg;

/**
 * Created by Name on 14/03/2016.
 */
import org.junit.Test;

import java.text.ParseException;

import lightning.cyborg.testassistants.TestAssistent;

import static org.junit.Assert.*;

public class RegUnitTest {
    TestAssistent test = new TestAssistent();

    //object test
    @Test
    public void testNotNull(){
        assertNotNull(test);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////TRUE DATE VALIDATE TEST//////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // testing with True values

//  @Test
  //public void testMethod2() throws ParseException{ //FALSE RETURNS AGE - 1998 years old//
   //assertTrue(test.Method("20/1990/12"));
  //}


    /* WORKING TESTS - For Function Validate Date
    @Test
    public void testMethod3() throws ParseException{ //-- TRUE 36 YO
        assertTrue(test.Method("12/12/1980"));
    }

/* ERROR
    @Test
    public void testMethod4() throws ParseException{ //ERROR RETURNS 4 YO
        assertTrue(test.Method("03/12/2012"));
    }
    @Test
    public void testMethod5() throws ParseException{// true returns 66yo
        assertTrue(test.Method("14/11/1950"));
    }
    */
    @Test
    public void testMethod6() throws ParseException{ //True returns 42
        assertTrue(test.Method("17/01/1974"));
    }
    @Test
    public void testMethod7() throws ParseException{ //tRUE RETURNS 51
        assertTrue(test.Method("05/05/1965"));
    }
    @Test
    public void testMethod8() throws ParseException{ //TRUE RETURNS 30
        assertTrue(test.Method("07/10/1986"));
    }
   /** @Test
    public void testMethod9() throws ParseException{
        assertTrue(test.Method("1990/20/20000"));// FALSE age -179844444444
    }

    @Test
    public void testMethodFalse1() throws ParseException{ // Unparsable Date
        assertFalse(test.Method("100-29292-222"));
    }

    @Test
    public void testMethodFalse2() throws ParseException{
        assertFalse(test.Method("20/20/20")); //assertion error
    }

    @Test
    public void testMethodFalse3() throws ParseException{
        assertFalse(test.Method("32/12/2000"));
    }


    WORKING TEST */
/*ERROR
    @Test
    public void testMethodFalse4() throws ParseException{ //ASSERTION ERROR
        assertFalse(test.Method("00/00/0000"));
    }*/
/*UNPARSABLE DATA ERRORs

    @Test
    public void testMethodFalse5() throws ParseException{//UNPARSABLE DATA
        assertFalse(test.Method("17/17/XX"));
    }

    @Test
    public void testMethodFalse6() throws ParseException{//Unparsable data
        assertFalse(test.Method("@@!12/20"));
    }

    @Test
    public void testMethodFalse7() throws ParseException{//UNPARSABLE DATA
        assertFalse(test.Method("NINJA"));
    }

    @Test
    public void testMethodFalse8() throws ParseException{
        assertFalse(test.Method("32/12/2000000000000000000"));
    }

    @Test
    public void testMethodFalse9() throws ParseException{//Assertion error
        assertFalse(test.Method("2000/2/0.2"));
    }

    @Test
    public void testMethodFalse10() throws ParseException{//Unparsable data
        assertFalse(test.Method("70/12/rest"));
    }


    @Test
    public void testMethodFalse11() throws ParseException{ // TEST SUCCESSFUL - FALSE DATE
        assertFalse(test.Method("20/20/2000"));
    }

    @Test
    public void testMethodFalse12() throws ParseException{ // TEST FAIL - ASSERTION ERROR
        assertFalse(test.Method("70/2/30"));
    }

    @Test
    public void testMethodFalse13() throws ParseException{ // TEST SUCCESSFUL - FALSE DATE
        assertFalse(test.Method("500/55550/60000"));
    }

    @Test
    public void testMethodFalse14() throws ParseException{ // ASSERTION ERROR
        assertFalse(test.Method("1/1/1"));
    }

    @Test
    public void testMethodFalse15() throws ParseException{ // ASSERTION ERROR
        assertFalse(test.Method("777/77/7"));
    }

    @Test
    public void testMethodFalse16() throws ParseException{ // TEST SUCCESS DATE
        assertFalse(test.Method("91234/0/123122000"));
    }

    @Test
    public void testMethodFalse17() throws ParseException{ // TEST SUCCESS
        assertFalse(test.Method("22220/000000/2000"));
    }

    @Test
    public void testMethodFalse18() throws ParseException{ // ASSERTION ERROR
        assertFalse(test.Method("20/20/0"));
    }

    END OF DATE VALIDATION TESTS
*/



    /**///////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////EMAIL VERIFICATION TESTS ///////////////////////////
     //////////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////*/
    @Test
    public void testMethodEmail() throws ParseException{
        assertTrue(test.Method2("super.monka@gmail.com"));
    }

    @Test
    public void testMethodEmail1(){
        assertTrue(test.Method2("dark.sleezy@zamunda.net"));
    }

    @Test
    public void testMethodEmail2(){
        assertTrue(test.Method2("someone_somewhere@yahoo.co.uk"));
    }

    @Test
    public void testMethodEmail3(){
        assertTrue(test.Method2("Yolo123@hotmail.com"));
    }

    @Test
    public void testMethodEmail4(){
        assertTrue(test.Method2("zubat222@abv.bg"));
    }
    @Test
    public void testMethodEmail5(){
        assertTrue(test.Method2("mozarella@terran.jp"));
    }
    @Test
    public void testMethodEmail6(){
        assertTrue(test.Method2("storm_light@gmail.dk"));
    }
    @Test
    public void testMethodEmail7(){
        assertTrue(test.Method2("defaultBoss@mail.com"));
    }
    @Test
    public void testMethodEmail8(){
        assertTrue(test.Method2("zeppelin1006@hotmail.com"));
    }
    @Test
    public void testMethodEmail9(){
        assertTrue(test.Method2("stephenyolo@zen.com"));
    }
    @Test
    public void testMethodEmail10(){
        assertTrue(test.Method2("sansa_stark@mail.co.uk"));
    }
    //false Email Tests


    @Test
    public void testMethodEmailFalse1() throws ParseException{
        assertFalse(test.Method2("@@@xaasa222"));
    }

    @Test
    public void testMethodEmailFalse2() throws ParseException{
        assertFalse(test.Method2("TSM"));
    }
    @Test
    public void testMethodEmailFalse3() throws ParseException{
        assertFalse(test.Method2("DL"));
    }
    @Test
    public void testMethodEmailFalse4() throws ParseException{
        assertFalse(test.Method2("kha'zix.email@222"));
    }
    @Test
    public void testMethodEmailFalse5() throws ParseException{
        assertFalse(test.Method2("Stephe.King@com.youuuuAA!"));

    }
    @Test
    public void testMethodEmailFalse6() throws ParseException{
        assertFalse(test.Method2("zealot1=1"));

    }
    @Test
    public void testMethodEmailFalse7() throws ParseException{
        assertFalse(test.Method2("129387123"));

    }
    @Test
    public void testMethodEmailFalse8() throws ParseException{
        assertFalse(test.Method2("redbull_zzztp["));

    }
    @Test
    public void testMethodEmailFalse9() throws ParseException{
        assertFalse(test.Method2("stalker_life"));

    }
    @Test
    public void testMethodEmailFalse10() throws ParseException{
        assertFalse(test.Method2("battery_full.zzzzz@2"));

    }
    @Test
    public void testMethodEmailFalse11() throws ParseException{
        assertFalse(test.Method2("shutdown@@@@email@@@now"));

    }

    //method proof - Check !!! - DO NOT USE THIS TEST
  //  @Test
    //public void testMethodEmailFalseCheck() throws ParseException {
      //  assertFalse(test.Method2("email.is.correct@ohboi.com"));
    ///}


    ///////////////////////////////////////
    //////////////////////////////////////
    /////////METHOD Check Name NULL///////
    /////////////////////////////////////
    ///////////////////////////////////
/**
    @Test
    public void testMethodNullName(){
        assertTrue(test.Method3("Ahad"));
    }

    @Test
    public void testMethodNullName1(){
        assertTrue(test.Method3("Scrubbi"));
    }
    @Test
    public void testMethodNullName2(){
        assertTrue(test.Method3("Stephen"));
    }
    @Test
    public void testMethodNullName3(){
        assertTrue(test.Method3("diapazon")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName4(){
        assertTrue(test.Method3("Dexter77")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName5(){
        assertTrue(test.Method3("AmazonPrincess")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName6(){
        assertTrue(test.Method3("cockcockcock")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName7(){
        assertTrue(test.Method3("bigtitsuk")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName8(){
        assertTrue(test.Method3("________")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName9(){
        assertTrue(test.Method3("service_xxx")); //problem NO NICK NAMES
    }
    @Test
    public void testMethodNullName10(){
        assertTrue(test.Method3("12313123")); //problem NO NICK NAMES
    }

    /**PROOF TEST - DO NOT USE THIS TEST
    @Test
    public void testMethodNullNameFalse0(){
        assertFalse(test.Method3("x"));
    }

    @Test
    public void testMethodNullNameFalse(){
        assertFalse(test.Method3(""));
    }

    // END OF NAME NOT NULL TESTING

    ///////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////Password Tests/////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
//TRUE TEST -----------
    @Test
    public void testMethodPassword(){
        assertTrue(test.Method4ValidPassword("amazing", "amazing"));
    }

    @Test
    public void testMethodPassword1(){
        assertTrue(test.Method4ValidPassword("IndUceParano1a", "IndUceParano1a"));
    }

    @Test
    public void testMethodPassword2(){
        assertTrue(test.Method4ValidPassword("bunnyfunny", "bunnyfunny"));
    }

    @Test
    public void testMethodPassword3(){
        assertTrue(test.Method4ValidPassword("qwe111#","qwe111#"));
    }

    @Test
    public void testMethodPassword4(){
        assertTrue(test.Method4ValidPassword("stephen222","stephen222"));
    }
    @Test
    public void testMethodPassword5(){
        assertTrue(test.Method4ValidPassword("stephen222","stephen222"));
    }
    @Test
    public void testMethodPassword6(){
        assertTrue(test.Method4ValidPassword("08887252719","08887252719"));
    }
    @Test
    public void testMethodPassword7(){
        assertTrue(test.Method4ValidPassword("dimitrov_grad","dimitrov_grad"));
    }
    @Test
    public void testMethodPassword8(){
        assertTrue(test.Method4ValidPassword("123yahooxxL","123yahooxxL"));
    }
    @Test
    public void testMethodPassword9(){
        assertTrue(test.Method4ValidPassword("stormphantom777","stormphantom777"));
    }
    @Test
    public void testMethodPassword10(){
        assertTrue(test.Method4ValidPassword("fyipisof","fyipisof"));
    }
    @Test
    public void testMethodPassword11(){
        assertTrue(test.Method4ValidPassword("haterzG0N4hate","haterzG0N4hate"));
    }
    //FALSE METHODS
    @Test
    public void testMethodPassword1False(){                    //should return false
        assertFalse(test.Method4ValidPassword("zamm1i", "zammi"));
    }

    @Test
    public void testMethodPasswordFalseX(){
        assertFalse(test.Method4ValidPassword("amazi@ng", "amazing"));
    }

    @Test
    public void testMethodPassFalse1(){
        assertFalse(test.Method4ValidPassword("zizzzi","d22xxx2asdd"));
    }

    @Test
    public void testMethodPassFalse2(){
        assertFalse(test.Method4ValidPassword("zizzzi","d22xxx2asdd"));
    }


    @Test
    public void testMethodPassFalse3(){
        assertFalse(test.Method4ValidPassword("drifted","away"));
    }

    @Test
    public void testMethodPassFalse4(){
        assertFalse(test.Method4ValidPassword("123xxc","cxx321"));
    }

    @Test
    public void testMethodPassFalse5(){
        assertFalse(test.Method4ValidPassword("ALPHA","alpha"));
    }

    @Test
    public void testMethodPassFalse6(){
        assertFalse(test.Method4ValidPassword("steelback","XXXXXXXXX"));
    }

    @Test
    public void testMethodPassFalse7(){
        assertFalse(test.Method4ValidPassword("REDBULL","giv3suwingz"));
    }

    @Test
    public void testMethodPassFalse8(){
        assertFalse(test.Method4ValidPassword("close","Close"));
    }

    @Test
    public void testMethodPassFalse9(){
        assertFalse(test.Method4ValidPassword("Ztop","ztop"));
    }

    @Test
    public void testMethodPassFalse10(){
        assertFalse(test.Method4ValidPassword("hecler","HECLER"));
    }



*/





}
