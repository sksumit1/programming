package com.sumit.programs;

import java.util.HashMap;
import java.util.Map;

public class MinCoinsNeeded {
    Map<Integer,Integer> amountMinMap = new HashMap<Integer,Integer>();
    public int coinChange(int[] coins, int amount) {
        int min = minCoinsNeeded(coins,new HashMap<Integer,Integer>(),amount);
        if(min == Integer.MAX_VALUE) {
            min = -1;
        }
        return min;
    }
    
    public int minCoinsNeeded(int[] coins, HashMap<Integer,Integer> selectedcoins, int amount) {
        if(amount==0) {
            return 0;
        } else if(amount < 0) {
            return Integer.MAX_VALUE;
        } else {
            int min=Integer.MAX_VALUE;
            int selectedCoin = -1;
            for(int i=0;i<coins.length;i++) {
                int coin = coins[i];
                int newAmt = amount - coin;
                Integer coinCount = amountMinMap.get(newAmt);
                if(coinCount == null) {
                    coinCount = minCoinsNeeded(coins,selectedcoins,newAmt);
                    amountMinMap.put(newAmt,coinCount);
                } 
                if(coinCount != Integer.MAX_VALUE) {
                    coinCount++;
                }
                if(coinCount < min) {
                    min = coinCount;
                    selectedCoin = coin;
                }
            }
            Integer selected = selectedcoins.get(selectedCoin);
            if(selected == null) {
                selected = 0;
            }
            selectedcoins.put(selectedCoin,selected+1);
            return min;
        }
    }
}
