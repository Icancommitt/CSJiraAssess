//import needed methods
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.query.Query
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.project.Project
import com.atlassian.jira.project.ProjectManager

//define variables for needed underlying services
def customFieldName = "Channel"  // Replace with your custom field name
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def searchService = ComponentAccessor.getComponent(SearchService)
def projectManager = ComponentAccessor.getProjectManager()
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def TotaljqlQuery = "Channel is NOT EMPTY"
def jqlQueryParser2 = ComponentAccessor.getComponent(JqlQueryParser)
def query2 = jqlQueryParser2.parseQuery(TotaljqlQuery)

// Execute the search
def searchResults = searchService.search(user, query2, PagerFilter.getUnlimitedFilter())

// Count the number of times the custom field is used
def totalUsageCountSys = searchResults.total
 
CustomField customField = customFieldManager.getCustomFieldObjectsByName(customFieldName).first()

//if coded custom field is not found in the system, print this warning
if (!customField) {
    return "Custom field not found!"
}

def result = ""

//iterate through projects with defined JQL query
projectManager.getProjectObjects().each { project ->
    def jqlQuery = "project = '${project.key}' AND '${customFieldName}' IS NOT EMPTY"
    def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
    def query = jqlQueryParser.parseQuery(jqlQuery)
    def cfCount2 = Issues.count("project = '${project.key}' AND Channel is NOT Empty")
    
//if query returns positive results keep counting per project and displaying it in a list
if (query && searchService.searchCount(user, query) > 0) {

       //result for each project
        result += "${project.key}: ${customFieldName} is used ${cfCount2} times</br>"  
        

    } 
}

//result for total count for all projects
result += "<br>Total number of times this custom field is used: ${totalUsageCountSys}</br>"

//show total result per each project and then total per all projects
return result
