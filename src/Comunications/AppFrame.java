package Comunications;

import java.io.Serializable;

public class AppFrame implements Serializable{
    private AppFrameType appFrameType;
    private Object object;
    
    public AppFrame (AppFrameType appFrameType, Object object){
        this.appFrameType = appFrameType;
        this.object = object;
    }

    public AppFrameType getAppFrameType() {
        return appFrameType;
    }

    public void setAppFrameType(AppFrameType appFrameType) {
        this.appFrameType = appFrameType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
