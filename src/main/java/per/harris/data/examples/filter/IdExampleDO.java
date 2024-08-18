package per.harris.data.examples.filter;

import per.harris.data.core.pojo.BaseDataModel;

public class IdExampleDO extends BaseDataModel {
    private String id;

    public IdExampleDO(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


}
