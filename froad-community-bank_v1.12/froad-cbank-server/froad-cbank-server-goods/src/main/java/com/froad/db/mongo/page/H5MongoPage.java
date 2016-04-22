package com.froad.db.mongo.page;

public class H5MongoPage extends MongoPage {
    
    public H5MongoPage(){
        super();
    }
    
    public H5MongoPage(int pageNumber, int pageSize) {
        super(pageNumber,pageSize);
    }

    /**
     * 是否还有下一页
     */
    private boolean hasNext;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
