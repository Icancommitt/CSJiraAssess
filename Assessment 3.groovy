//import needed packages
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.ModifiedValue
 
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def issueManager = ComponentAccessor.getIssueManager()

// Define the issue key
String issueKey = "UETENT-162" //replace with your issue key where the source and target fields have values set already
MutableIssue issue = issueManager.getIssueObject(issueKey)

if (issue) {
    // Define source and target custom fields using their names
    def sourceCustomField = customFieldManager.getCustomFieldObjectByName("MultiSLF1") //replace with your source Select List Multi Choice custom field
    def targetCustomField = customFieldManager.getCustomFieldObjectByName("MultiSLF2") //replace with your target Select List Multi Choice custom field
    
    // Get the value(s) of the source field
    def sourceValues = issue.getCustomFieldValue(sourceCustomField)
    
    if (sourceValues) {
        // Set the value(s) to the target field

        targetCustomField.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(targetCustomField), sourceValues), new DefaultIssueChangeHolder()) 
        //check the fields on the issue after running this and you should see the values from source field have been copied to target field

    }
}
