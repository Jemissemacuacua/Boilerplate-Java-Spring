package com.app.builderplante.shared;

public abstract class BaseController<T extends BaseEntity, ID> {

    protected abstract BaseService<T, ID> getService();
}
