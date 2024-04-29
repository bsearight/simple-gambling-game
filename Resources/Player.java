package Resources;
public class Player
{
    int id;
    String username;
    String pHash;
    int balance;
    int betValue;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPHash() {
        return this.pHash;
    }

    public void setPHash(String pHash) {
        this.pHash = pHash;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void setBetValue(int betValue)
    {
        this.betValue = betValue;
    }
    public int getBetValue()
    {
        return this.betValue;
    }
}