/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datacommfinal;

/**
 *
 * @author Dan
 */
public class DataCommFinal 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
          Router router = new Router();
          
	        int result = router.parseCmd("ADD prefix=157.29.32.0/20, route=1");
	       
	        int result2 = router.parseCmd("ADD prefix=157.29.48.0/21, route=2");
	       
	        int result3 = router.parseCmd("ADD prefix=157.29.48.0/20, route=3");
	       
	        int result4 = router.parseCmd("ADD prefix=157.0.0.0/8, route=4");
	        
	        
	        int result5 = router.parseCmd("ADD prefix=157.29.62.0/23, route=NULL");
	        int result6 = router.parseCmd("ADD prefix=0/0, route=5");
	        int result7 = router.parseCmd("LOOKUP dest=157.29.47.95");
	        
	        //System.out.println("Result 6: " + result6);
	        
	        int result8 = router.parseCmd("LOOKUP dest=157.29.56.111");
	        int result9 = router.parseCmd("LOOKUP dest=157.29.40.17");
	        int result10 = router.parseCmd("LOOKUP dest=157.29.49.20");
	        int result11 = router.parseCmd("LOOKUP dest=157.30.56.111");
	        int result12 = router.parseCmd("LOOKUP dest=128.61.52.1");
	        
	        int result13 = router.parseCmd("LOOKUP dest=157.29.63.1");
	        //int result3 = router.parseCmd("DEL prefix: 157.29.32.0/21, route=1");
	      
	        
	        
	        System.out.println("Result 1: " + result);
	       
	        System.out.println("Result 2: " + result2);
	        
	        System.out.println("Result 3: " + result3);
	        
	        System.out.println("Result 4: " + result4);
	       
	        System.out.println("Result 5: " + result5);
	        
	        System.out.println("Result 6: " + result6);
	        System.out.println("Result 7: " + result7);
	        System.out.println("Result 8: " + result8);
	        System.out.println("Result 9: " + result9);
	        System.out.println("Result 10: " + result10);
	        
	        
	        System.out.println("Result 11: " + result11);
	        System.out.println("Result 12: " + result12);
	        System.out.println("Result 13: " + result13);
	       // System.out.println("Result 14: " + result2);
	        
	       // System.out.println("Result 3: " + result3);
                       
    }
    
}
