package ru.kiero.nomad.entity;

public enum RelationStage {
    ALLY(80),
    RESPECT(50),
    FRIEND(1),
    NEUTRAL(0),
    DISLIKE(-49),
    ENEMY(-100);

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
