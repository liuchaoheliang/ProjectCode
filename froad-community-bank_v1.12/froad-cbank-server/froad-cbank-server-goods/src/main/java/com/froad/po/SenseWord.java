package com.froad.po;

import java.io.Serializable;

public class SenseWord implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4227492009839503475L;
    
    private Long id;
    private String word;
    private Boolean isEnable;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public Boolean getIsEnable() {
        return isEnable;
    }
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

}
