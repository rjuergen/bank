/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity.util;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Jürgen Reichl
 */
public class EntityUtilsTest {
    
    public EntityUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of hashPassword method, of class EntityUtils.
     */
    @org.junit.Test
    public void testHashPassword() throws Exception {
        System.out.println("hashPassword");
        String password = "secretPassword";
        String salt = "br";        
        String result = EntityUtils.hashPassword(password, salt);
        String result2 = EntityUtils.hashPassword(password, salt);
        Assert.assertEquals(result2, result);
        Assert.assertNotEquals(password, result);
    }

    /**
     * Test of createRandomString method, of class EntityUtils.
     */
    @org.junit.Test
    public void testCreateRandomString() {
        System.out.println("createRandomString");
        int byteLength = 20;        
        String result = EntityUtils.createRandomString(byteLength);
        Set<String> results = new HashSet<String>(100);
        for(int i = 0; i<100; i++) {
            results.add(EntityUtils.createRandomString(byteLength));
        }   
        Assert.assertEquals(results.size(), 100);        
    }

    /**
     * Test of createRandomUUID method, of class EntityUtils.
     */
    @org.junit.Test
    public void testCreateRandomUUID() {
        System.out.println("createRandomUUID");
        Set<String> UUIDs = new HashSet<String>(100);
        for(int i = 0; i<100; i++) {
            UUIDs.add(EntityUtils.createRandomUUID());
        }   
        Assert.assertEquals(UUIDs.size(), 100);        
    }
    
    /**
     * Test of createAccountNumber method, of class EntityUtils.
     */
    @org.junit.Test
    public void testCreateAccountNumber() {
        System.out.println("createAccountNumber");
        String accountNumber = EntityUtils.createAccountNumber(123L);
        Assert.assertEquals("00000000123", accountNumber);        
    }
        
    
}
