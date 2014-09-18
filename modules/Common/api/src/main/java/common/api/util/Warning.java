package common.api.util;

/**
 * Created by Onis on 15/08/14.
 */
public class Warning {

    String id;
    String title;
    Severity severity;
    String simpleDescription;
    String verboseDescription;

    public Warning() {
    }

    public Warning(String id, String title, Severity severity, String simpleDescription, String verboseDescription) {
        this.id = "WARNING_" + id;
        this.title = title;
        this.severity = severity;
        this.simpleDescription = simpleDescription;
        this.verboseDescription = verboseDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = "WARNING_" + id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getSimpleDescription() {
        return simpleDescription;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }

    public String getVerboseDescription() {
        return verboseDescription;
    }

    public void setVerboseDescription(String verboseDescription) {
        this.verboseDescription = verboseDescription;
    }
}
