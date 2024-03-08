package Comunications;

import java.io.Serializable;

public class DataFrame implements Serializable {
    private DataFrameType dataFrameType;
    private Object sendObject;
    
    public DataFrame(DataFrameType dataFrameType, Object object){
        this.dataFrameType = dataFrameType;
        this.sendObject = object;
    }

    public DataFrameType getDataFrameType() {
        return dataFrameType;
    }

    public void setDataFrameType(DataFrameType dataFrameType) {
        this.dataFrameType = dataFrameType;
    }

    public Object getSendObject() {
        return sendObject;
    }

    public void setSendObject(Object sendObject) {
        this.sendObject = sendObject;
    }
}
