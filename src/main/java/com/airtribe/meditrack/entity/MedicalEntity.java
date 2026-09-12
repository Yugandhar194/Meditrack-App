package com.airtribe.meditrack.entity;

import java.io.Serializable;

public abstract class MedicalEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public abstract String getRole();
}
