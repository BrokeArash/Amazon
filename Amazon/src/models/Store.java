package models;

public class Store {


    private String brandName;
    private String password;
    private String email;

    public Store(String brandName, String password, String email) {
        this.brandName = brandName;
        this.password = password;
        this.email = email;
    }

    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public static boolean isEmailUnique (String email) {
        return Store.getStoreByEmail(email) == null;
    }

    public static Store getStoreByEmail(String email) {
        for (Store store : App.stores) {
            if (store.getEmail().equals(email)) {
                return store;
            }
        }
        return null;
    }


}
