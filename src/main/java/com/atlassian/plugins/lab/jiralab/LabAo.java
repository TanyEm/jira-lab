package com.atlassian.plugins.lab.jiralab;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface LabAo extends Entity
{
    String getString();

    void setString(String stringData);

    String getDate();

    void setDate(String dateData);
}