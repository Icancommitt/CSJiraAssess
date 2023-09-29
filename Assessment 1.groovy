import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.bc.issue.search.SearchService

// Define the custom field name you want to count
def customFieldName = &quot;Channel&quot; //replace with your own custom field name

// Define variables to get currently logged in user and underlying searchservice for JQL
def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def searchService = ComponentAccessor.getComponent(SearchService)

// Create a JQL query to search for all issues that have the custom field set
def jqlQuery = &quot;Channel is not EMPTY&quot; //replace with your own customfield and query as needed

// Parse the JQL query
def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser)
def query = jqlQueryParser.parseQuery(jqlQuery)

// Execute the search
def searchResults = searchService.search(user, query, PagerFilter.getUnlimitedFilter())

// Count the number of times the custom field is used
def totalUsageCountSys = searchResults.total

// Print the result (will show in both Result and Logs Section)
log.warn(&quot;Total usage count of custom field &#39;$customFieldName&#39;: $totalUsageCountSys&quot;)
return &quot;Total usage count of customfield &quot; + customFieldName+ &quot; across the system is &quot; + totalUsageCountSys
