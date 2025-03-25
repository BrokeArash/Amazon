package models;

public class Store extends User{


    private String brandName;

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
