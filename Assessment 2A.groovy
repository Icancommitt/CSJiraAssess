//import needed packages
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.query.Query
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.jql.parser.JqlQueryParser

//define variables required for underying customfield, searchservice, projects and currently logged in user
def customFieldName = "Channel"  // Replace with your custom field name
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def searchService = ComponentAccessor.getComponent(SearchService)
def projectManager = ComponentAccessor.getProjectManager()
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()

CustomField customField = customFieldManager.getCustomFieldObjectsByName(customFieldName).first()

//if customfield from code doesn't exist in system then print result
if (!customField) {
    return "Custom field not found!"
}

int projectCount = 0

//iterate through every project with the defined jql query
projectManager.getProjectObjects().each { project ->
    def jqlQuery = "project = '${project.key}' AND '${customFieldName}' IS NOT EMPTY"
    def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
    def query = jqlQueryParser.parseQuery(jqlQuery)

//if query returns positive result then add it to the projectCount variable and rinse and repeat    
if (query && searchService.searchCount(user, query) > 0) {
        projectCount++
    }
}

//print result in the Result section
return "Number of projects where '${customFieldName}' is not empty: ${projectCount}."
