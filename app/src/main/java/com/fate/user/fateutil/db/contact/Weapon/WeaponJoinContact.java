package com.fate.user.fateutil.db.contact.Weapon;

public class WeaponJoinContact {
    private int Id;
    private int servantId;
    private int weaponId;

    public WeaponJoinContact(){ }

    public WeaponJoinContact(int id, int servant_id, int weapon_id) {
        this.Id = id;
        this.servantId = servant_id;
        this.weaponId = weapon_id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getServantId() {
        return servantId;
    }

    public void setServantId(int servantId) {
        this.servantId = servantId;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }
}
