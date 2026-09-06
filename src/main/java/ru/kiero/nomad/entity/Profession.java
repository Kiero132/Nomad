package ru.kiero.nomad.entity;

public enum Profession {
    NONE(0),
    HUNTER(1),
    SHAMAN(2),
    TRADER(3);

    private final int id;

    Profession(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public static Profession fromId(int id){
        for (Profession p : values()){
            if(p.id == id) return p;
        }
        return NONE;
    }
}
