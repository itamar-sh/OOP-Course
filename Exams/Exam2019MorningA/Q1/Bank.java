package Exams.Exam2019MorningA.Q1;

import java.util.HashMap;

public class Bank {
    public enum CoinType {FIAT, CRYPTO}
    private static HashMap<String, Coin> walletCoins = new HashMap<>();
    public static Coin getCoin(String name, CoinType type){
        if(walletCoins.containsKey(name)){
            return walletCoins.get(name);
        } else {
            Coin coin = Bank.coinsFactory(name, type);
            walletCoins.put(name, coin);
            return coin;
        }

    }

    private static Coin coinsFactory(String name, CoinType type) {
        switch(type){
            case CRYPTO:
                return new CryptoCurrency(name);
            case FIAT:
                return new FiatCurrency(name);
            default:
                return null;
        }
    }
}
