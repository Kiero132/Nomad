package ru.kiero.nomad.entity;

public enum RelationStage {
    ENEMY(-100),
    DISLIKE(-49),
    NEUTRAL(0),
    FRIEND(1),
    RESPECT(50),
    ALLY(80);

    private final int relation;

    RelationStage(int relation){
        this.relation = relation;
    }

    public int getId(){
        return relation;
    }

    public static RelationStage of(int relation){
        for (RelationStage p : values()){
            if(relation >= p.relation) return p;
        }
        return NEUTRAL;
    }
}
