package com.flyingtechs.studentManagement.dto;

public class AbstractDTO<E> {

    private E id;

    public E getId() {
        return id;
    }

    public void setId(E id) {
        this.id = id;
    }
}