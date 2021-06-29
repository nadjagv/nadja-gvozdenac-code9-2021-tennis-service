package com.nadjagv.adminloginservice.exception;

public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer resourceId;

    public ResourceConflictException(Integer integer, String message) {
        super(message);
        this.setResourceId(integer);
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}
