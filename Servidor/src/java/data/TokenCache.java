/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Map;
import javax.ejb.Singleton;

/**
 *
 * @author JoseRullva
 */
@Singleton
public class TokenCache {
    private static Map<String, Integer> cache;
    
    public static boolean isInCache(String idtoken) {
        Integer exp = cache.get(idtoken);
        if (exp != null) {
            if (System.currentTimeMillis() < exp) {
                return true;
            } else {
                cache.remove(idtoken);
                return false;
            }
        }
        return false;
    }
    
    public static void insertInCache(String idtoken, Integer exp) {
        cache.put(idtoken, exp);
    }
}
